package com.automationtools.soapui;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.GeneralSecurityException;
import org.apache.commons.ssl.OpenSSL;
import org.apache.xmlbeans.XmlOptions;
import com.eviware.soapui.SoapUI;
import com.eviware.soapui.config.ProjectConfig;
import com.eviware.soapui.config.SoapuiProjectDocumentConfig;
import com.eviware.soapui.impl.settings.XmlBeansSettingsImpl;
import com.eviware.soapui.impl.wsdl.WsdlProject;
import com.eviware.soapui.model.project.SaveStatus;
import com.eviware.soapui.settings.ProjectSettings;
import com.eviware.soapui.settings.WsdlSettings;
import com.eviware.soapui.support.StringUtils;
import com.eviware.soapui.support.UISupport;

/**
 * This implementation is directly copied from SoapUI's 
 * {@link WsdlProject#saveIn(java.io.File)} code in order to 
 * implement {@link #save(OutputStream)}.
 * 
 * @author 	Melvin Garcia
 * @since 	1.0.0
 */
public class CustomWsdlProject extends WsdlProject {

	public CustomWsdlProject(InputStream inputStream) throws Exception {
		super();
		loadProjectFromInputStream(inputStream);
	}
	
	/**
	 * Save this document to an {@code OutputStream}.
	 * 
	 * @param out			Target {@code OutputStream}
	 * @return
	 * @throws IOException	Error occurred during save process
	 */
	public SaveStatus save(OutputStream out) throws IOException {
		beforeSave();
		
		SoapuiProjectDocumentConfig projectDocument = (SoapuiProjectDocumentConfig) getProjectDocument().copy();
		// check for caching
        if (!getSettings().getBoolean(WsdlSettings.CACHE_WSDLS)) {
            // no caching -> create copy and remove definition cachings
        	removeDefinitionCaches(projectDocument);
        }
        
        removeProjectRoot(projectDocument);

        if (hasBeenSuccessfullyDecrypted(projectDocument) && hasEncryptionPassword()) {
            ProjectConfig encryptedProjectConfig = encrypt(projectDocument);
            projectDocument.setSoapuiProject(encryptedProjectConfig);
        }

        XmlOptions options = new XmlOptions();
        if (SoapUI.getSettings().getBoolean(WsdlSettings.PRETTY_PRINT_PROJECT_FILES)) {
            options.setSavePrettyPrint();
        }

        projectDocument.getSoapuiProject().setSoapuiVersion(SoapUI.SOAPUI_VERSION);
        projectDocument.save(out, options);
        out.flush();
		return SaveStatus.SUCCESS;
	}
	
	private void removeProjectRoot(SoapuiProjectDocumentConfig projectDocument) {
        XmlBeansSettingsImpl tempSettings = new XmlBeansSettingsImpl(this, null, projectDocument.getSoapuiProject().getSettings());
        tempSettings.clearSetting(ProjectSettings.PROJECT_ROOT);
    }
	
	private boolean hasBeenSuccessfullyDecrypted(SoapuiProjectDocumentConfig projectDocument) {
        // if it has encryptedContend that means it is not decrypted correctly( bad
        // password, etc ), so do not encrypt it again.
        return projectDocument.getSoapuiProject().getEncryptedContent() == null;
    }
	
	private boolean hasEncryptionPassword() {
        String passwordForEncryption = getSettings().getString(ProjectSettings.SHADOW_PASSWORD, null);
        return StringUtils.hasContent(passwordForEncryption);
    }
	
	private ProjectConfig encrypt(SoapuiProjectDocumentConfig projectDocument) throws IOException {
        // check for encryption
        String passwordForEncryption = getSettings().getString(ProjectSettings.SHADOW_PASSWORD, null);

        if (hasEncryptionPassword()) {
            // we have password so do encryption
            try {
                String data = getConfig().xmlText();
                String encryptionAlgorithm = "des3";
                byte[] encrypted = OpenSSL.encrypt(encryptionAlgorithm, passwordForEncryption.toCharArray(), data.getBytes());
                ProjectConfig newProjectConfig = ProjectConfig.Factory.newInstance();
                ProjectConfig soapuiProject = projectDocument.getSoapuiProject();
                newProjectConfig.setName(soapuiProject.getName());
                newProjectConfig.setEncryptedContent(encrypted);
                newProjectConfig.setEncryptedContentAlgorithm(encryptionAlgorithm);
                return newProjectConfig;
            } catch (GeneralSecurityException e) {
                UISupport.showErrorMessage("Encryption Error");
            }
        }
        return projectDocument.getSoapuiProject();
    }

}

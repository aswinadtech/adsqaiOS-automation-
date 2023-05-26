package com.twc.ios.app.general;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;

import com.twc.ios.app.charlesfunctions.CharlesConfiguration;
import com.twc.ios.app.charlesfunctions.CharlesProxy;
import com.twc.ios.app.charlesfunctions.CharlesProxyException;
import com.twc.ios.app.charlesfunctions.CharlesConfiguration.Protocol;
import com.twc.ios.app.charlesfunctions.CharlesConfiguration.RewriteRuleReplaceType;
import com.twc.ios.app.charlesfunctions.CharlesConfiguration.RewriteRuleType;



public class TwcIosBaseTest extends CharlesProxy{

	//private static final MobileAutomationLogger LOGGER = new MobileAutomationLogger(TwcIosBaseTest.class);
	private boolean freshInstallDone = false;
	 private CharlesConfiguration mapConfig;

	/**
	 * Create a Charles configuration to rewrite privacy regime to the given regime (GDPR) values. 
	 * @param fileName
	 *            - Name of file (.config extension) to store configuration. Will be created in user.dir
	 * @return Config Files (for deletion in After method)
	 */
	public File rewriteRuleToEnableGDPR(String fileName) {
		final List<File> configFiles = new ArrayList<File>();
		final File parentDir = new File(Constants.PATH_USER_HOME);
		parentDir.mkdirs();
		final File configFile = new File(parentDir, fileName);
		configFile.setWritable(true);

		// Create Charles config with header response rewrite for twc-privacy:exempt -> twc-privacy:gdpr
		CharlesConfiguration config = new CharlesConfiguration();
		//config.addRule(RewriteRuleType.MODIFY_HEADER, false, true, "twc-privacy", false, "exempt", false, false, false, "twc-privacy", false, "gdpr", false, RewriteRuleReplaceType.ONLY_FIRST);
		config.addRule(RewriteRuleType.MODIFY_HEADER, false, true, "twc-privacy", false, "[A-Za-z0-9\\.\\-]+", true, false, false, "twc-privacy", false, "gdpr", false, RewriteRuleReplaceType.ONLY_FIRST);
		config.addRule(RewriteRuleType.MODIFY_HEADER, false, true, "twc-geoip-country", false, "[A-Za-z0-9\\.\\-]+", true, false, false, "twc-geoip-country", false, "UK", false, RewriteRuleReplaceType.ONLY_FIRST);
		config.addLocation(Protocol.HTTPS, "dsx.weather.com", "", "/cms/*/privacy/en_US/twc-ios-flagship/*", "");

		config.saveConfigurations(fileName);


		return configFile;
	}
	
	/**
	 * Create a Charles configuration to rewrite privacy regime to the given regime (GDPR) values. 
	 * @param fileName
	 *            - Name of file (.config extension) to store configuration. Will be created in user.dir
	 * @return Config Files (for deletion in After method)
	 */
	public File rewriteRuleToEnableGDPRAndroid(String fileName) {
		final List<File> configFiles = new ArrayList<File>();
		final File parentDir = new File(Constants.PATH_USER_HOME);
		parentDir.mkdirs();
		final File configFile = new File(parentDir, fileName);
		configFile.setWritable(true);

		// Create Charles config with header response rewrite for twc-privacy:exempt -> twc-privacy:gdpr
		CharlesConfiguration config = new CharlesConfiguration();
		//config.addRule(RewriteRuleType.MODIFY_HEADER, false, true, "twc-privacy", false, "exempt", false, false, false, "twc-privacy", false, "gdpr", false, RewriteRuleReplaceType.ONLY_FIRST);
		config.addRule(RewriteRuleType.MODIFY_HEADER, false, true, "twc-privacy", false, "[A-Za-z0-9\\.\\-]+", true, false, false, "twc-privacy", false, "gdpr", false, RewriteRuleReplaceType.ONLY_FIRST);
		config.addRule(RewriteRuleType.MODIFY_HEADER, false, true, "twc-geoip-country", false, "[A-Za-z0-9\\.\\-]+", true, false, false, "twc-geoip-country", false, "UK", false, RewriteRuleReplaceType.ONLY_FIRST);
		config.addLocation(Protocol.HTTPS, "dsx.weather.com", "", "/cms/*/privacy/en_US/*/*", "");

		config.saveConfigurations(fileName);


		return configFile;
	}
	
	/**
	 * Create a Charles configuration to rewrite privacy regime to the given regime (TURKEY i.e. TR-KVKK) values. 
	 * @param fileName
	 *            - Name of file (.config extension) to store configuration. Will be created in user.dir
	 * @return Config Files (for deletion in After method)
	 */
	public File rewriteRuleToEnableTURKEY(String fileName) {
		final List<File> configFiles = new ArrayList<File>();
		final File parentDir = new File(Constants.PATH_USER_HOME);
		parentDir.mkdirs();
		final File configFile = new File(parentDir, fileName);
		configFile.setWritable(true);

		// Create Charles config with header response rewrite for twc-privacy:exempt -> twc-privacy:gdpr
		CharlesConfiguration config = new CharlesConfiguration();
		//config.addRule(RewriteRuleType.MODIFY_HEADER, false, true, "twc-privacy", false, "exempt", false, false, false, "twc-privacy", false, "gdpr", false, RewriteRuleReplaceType.ONLY_FIRST);
		config.addRule(RewriteRuleType.MODIFY_HEADER, false, true, "twc-privacy", false, "[A-Za-z0-9\\.\\-]+", true, false, false, "twc-privacy", false, "tr-kvkk", false, RewriteRuleReplaceType.ONLY_FIRST);
		config.addRule(RewriteRuleType.MODIFY_HEADER, false, true, "twc-geoip-country", false, "[A-Za-z0-9\\.\\-]+", true, false, false, "twc-geoip-country", false, "TR", false, RewriteRuleReplaceType.ONLY_FIRST);
		config.addLocation(Protocol.HTTPS, "dsx.weather.com", "", "/cms/*/privacy/en_US/twc-ios-flagship/*", "");

		config.saveConfigurations(fileName);


		return configFile;
	}
	
	/**
	 * Create a Charles configuration to rewrite privacy regime to the given regime (TURKEY i.e. TR-KVKK) values. 
	 * @param fileName
	 *            - Name of file (.config extension) to store configuration. Will be created in user.dir
	 * @return Config Files (for deletion in After method)
	 */
	public File rewriteRuleToEnableTURKEYAndroid(String fileName) {
		final List<File> configFiles = new ArrayList<File>();
		final File parentDir = new File(Constants.PATH_USER_HOME);
		parentDir.mkdirs();
		final File configFile = new File(parentDir, fileName);
		configFile.setWritable(true);

		// Create Charles config with header response rewrite for twc-privacy:exempt -> twc-privacy:gdpr
		CharlesConfiguration config = new CharlesConfiguration();
		//config.addRule(RewriteRuleType.MODIFY_HEADER, false, true, "twc-privacy", false, "exempt", false, false, false, "twc-privacy", false, "gdpr", false, RewriteRuleReplaceType.ONLY_FIRST);
		config.addRule(RewriteRuleType.MODIFY_HEADER, false, true, "twc-privacy", false, "[A-Za-z0-9\\.\\-]+", true, false, false, "twc-privacy", false, "tr-kvkk", false, RewriteRuleReplaceType.ONLY_FIRST);
		config.addRule(RewriteRuleType.MODIFY_HEADER, false, true, "twc-geoip-country", false, "[A-Za-z0-9\\.\\-]+", true, false, false, "twc-geoip-country", false, "TR", false, RewriteRuleReplaceType.ONLY_FIRST);
		config.addLocation(Protocol.HTTPS, "dsx.weather.com", "", "/cms/*/privacy/en_US/*/*", "");

		config.saveConfigurations(fileName);


		return configFile;
	}
	
	/**
	 * Create a Charles configuration to rewrite privacy regime to the given regime (SERBIA) values. For SERBIA, twc-privacy is 'GDPR' and geoip country is 'RS'
	 * @param fileName
	 *            - Name of file (.config extension) to store configuration. Will be created in user.dir
	 * @return Config Files (for deletion in After method)
	 */
	public File rewriteRuleToEnableSERBIA(String fileName) {
		final List<File> configFiles = new ArrayList<File>();
		final File parentDir = new File(Constants.PATH_USER_HOME);
		parentDir.mkdirs();
		final File configFile = new File(parentDir, fileName);
		configFile.setWritable(true);

		// Create Charles config with header response rewrite for twc-privacy:exempt -> twc-privacy:gdpr
		CharlesConfiguration config = new CharlesConfiguration();
		//config.addRule(RewriteRuleType.MODIFY_HEADER, false, true, "twc-privacy", false, "exempt", false, false, false, "twc-privacy", false, "gdpr", false, RewriteRuleReplaceType.ONLY_FIRST);
		config.addRule(RewriteRuleType.MODIFY_HEADER, false, true, "twc-privacy", false, "[A-Za-z0-9\\.\\-]+", true, false, false, "twc-privacy", false, "gdpr", false, RewriteRuleReplaceType.ONLY_FIRST);
		config.addRule(RewriteRuleType.MODIFY_HEADER, false, true, "twc-geoip-country", false, "[A-Za-z0-9\\.\\-]+", true, false, false, "twc-geoip-country", false, "RS", false, RewriteRuleReplaceType.ONLY_FIRST);
		config.addLocation(Protocol.HTTPS, "dsx.weather.com", "", "/cms/*/privacy/en_US/twc-ios-flagship/*", "");

		config.saveConfigurations(fileName);


		return configFile;
	}
	
	/**
	 * Create a Charles configuration to rewrite privacy regime to the given regime (SERBIA) values. For SERBIA, twc-privacy is 'GDPR' and geoip country is 'RS'
	 * @param fileName
	 *            - Name of file (.config extension) to store configuration. Will be created in user.dir
	 * @return Config Files (for deletion in After method)
	 */
	public File rewriteRuleToEnableSERBIAAndroid(String fileName) {
		final List<File> configFiles = new ArrayList<File>();
		final File parentDir = new File(Constants.PATH_USER_HOME);
		parentDir.mkdirs();
		final File configFile = new File(parentDir, fileName);
		configFile.setWritable(true);

		// Create Charles config with header response rewrite for twc-privacy:exempt -> twc-privacy:gdpr
		CharlesConfiguration config = new CharlesConfiguration();
		//config.addRule(RewriteRuleType.MODIFY_HEADER, false, true, "twc-privacy", false, "exempt", false, false, false, "twc-privacy", false, "gdpr", false, RewriteRuleReplaceType.ONLY_FIRST);
		config.addRule(RewriteRuleType.MODIFY_HEADER, false, true, "twc-privacy", false, "[A-Za-z0-9\\.\\-]+", true, false, false, "twc-privacy", false, "gdpr", false, RewriteRuleReplaceType.ONLY_FIRST);
		config.addRule(RewriteRuleType.MODIFY_HEADER, false, true, "twc-geoip-country", false, "[A-Za-z0-9\\.\\-]+", true, false, false, "twc-geoip-country", false, "RS", false, RewriteRuleReplaceType.ONLY_FIRST);
		config.addLocation(Protocol.HTTPS, "dsx.weather.com", "", "/cms/*/privacy/en_US/*/*", "");

		config.saveConfigurations(fileName);


		return configFile;
	}
	
	public File rewriteRuleToEnableGDPRWithLocale(String fileName) {
		final File parentDir = new File(Constants.PATH_USER_HOME);
		parentDir.mkdirs();
		final File configFile = new File(parentDir, fileName);
		configFile.setWritable(true);

		// Create Charles config with header response rewrite for twc-privacy:exempt ->
		// twc-privacy:gdpr
		CharlesConfiguration config = new CharlesConfiguration();
		config.addRule(RewriteRuleType.MODIFY_HEADER, false, true, "TWC-Privacy", false, "exempt", false, false, false,
				"TWC-Privacy", false, "gdpr", false, RewriteRuleReplaceType.ONLY_FIRST);
		config.addRule(RewriteRuleType.MODIFY_HEADER, false, true, "twc-geoip-country", false, "US", false, false,
				false, "twc-geoip-country", false, "FR", false, RewriteRuleReplaceType.ONLY_FIRST);
		config.addLocation(Protocol.HTTPS, "dsx.weather.com", "", "/cms/*/privacy/*/twc-ios-flagship/*", "");
		config.enableMacOsxProxy(true);
		config.saveConfigurations(fileName);

		return configFile;
	}
	
	/**
	 * Create a Charles configuration to rewrite privacy regime to the given regime (LGPD) values. 
	 * @param fileName
	 *            - Name of file (.config extension) to store configuration. Will be created in user.dir
	 * @return Config Files (for deletion in After method)
	 */
	public File rewriteRuleToEnableLGPD(String fileName) {
		final List<File> configFiles = new ArrayList<File>();
		final File parentDir = new File(Constants.PATH_USER_HOME);
		parentDir.mkdirs();
		final File configFile = new File(parentDir, fileName);
		configFile.setWritable(true);

		// Create Charles config with header response rewrite for twc-privacy:exempt -> twc-privacy:gdpr
		CharlesConfiguration config = new CharlesConfiguration();
		//config.addRule(RewriteRuleType.MODIFY_HEADER, false, true, "twc-privacy", false, "exempt", false, false, false, "twc-privacy", false, "lgpd", false, RewriteRuleReplaceType.ONLY_FIRST);
		config.addRule(RewriteRuleType.MODIFY_HEADER, false, true, "twc-privacy", false, "[A-Za-z0-9\\.\\-]+", true, false, false, "twc-privacy", false, "lgpd", false, RewriteRuleReplaceType.ONLY_FIRST);
		config.addRule(RewriteRuleType.MODIFY_HEADER, false, true, "twc-geoip-country", false, "[A-Za-z0-9\\.\\-]+", true, false, false, "twc-geoip-country", false, "BR", false, RewriteRuleReplaceType.ONLY_FIRST);
		//config.addRule(RewriteRuleType.MODIFY_HEADER, false, true, "twc-geoip-region", false, "[A-Za-z0-9\\.\\-]+", false, false, false, "twc-geoip-region", false, "SP", false, RewriteRuleReplaceType.ONLY_FIRST);
		config.addLocation(Protocol.HTTPS, "dsx.weather.com", "", "/cms/*/privacy/en_US/twc-ios-flagship/*", "");

		config.saveConfigurations(fileName);


		return configFile;
	}
	
	/**
	 * Create a Charles configuration to rewrite privacy regime to the given regime (LGPD) values. 
	 * @param fileName
	 *            - Name of file (.config extension) to store configuration. Will be created in user.dir
	 * @return Config Files (for deletion in After method)
	 */
	public File rewriteRuleToEnableLGPDAndroid(String fileName) {
		final List<File> configFiles = new ArrayList<File>();
		final File parentDir = new File(Constants.PATH_USER_HOME);
		parentDir.mkdirs();
		final File configFile = new File(parentDir, fileName);
		configFile.setWritable(true);

		// Create Charles config with header response rewrite for twc-privacy:exempt -> twc-privacy:gdpr
		CharlesConfiguration config = new CharlesConfiguration();
		//config.addRule(RewriteRuleType.MODIFY_HEADER, false, true, "twc-privacy", false, "exempt", false, false, false, "twc-privacy", false, "lgpd", false, RewriteRuleReplaceType.ONLY_FIRST);
		config.addRule(RewriteRuleType.MODIFY_HEADER, false, true, "twc-privacy", false, "[A-Za-z0-9\\.\\-]+", true, false, false, "twc-privacy", false, "lgpd", false, RewriteRuleReplaceType.ONLY_FIRST);
		config.addRule(RewriteRuleType.MODIFY_HEADER, false, true, "twc-geoip-country", false, "[A-Za-z0-9\\.\\-]+", true, false, false, "twc-geoip-country", false, "BR", false, RewriteRuleReplaceType.ONLY_FIRST);
		//config.addRule(RewriteRuleType.MODIFY_HEADER, false, true, "twc-geoip-region", false, "[A-Za-z0-9\\.\\-]+", false, false, false, "twc-geoip-region", false, "SP", false, RewriteRuleReplaceType.ONLY_FIRST);
		config.addLocation(Protocol.HTTPS, "dsx.weather.com", "", "/cms/*/privacy/en_US/*/*", "");

		config.saveConfigurations(fileName);


		return configFile;
	}
	
	/**
	 * Create a Charles configuration to rewrite privacy regime to the given regime (USA) values, region to WA (Washington)  when TB is not available
	 * @param fileName
	 *            - Name of file (.config extension) to store configuration. Will be created in user.dir
	 * @return Config Files (for deletion in After method)
	 */
	public File rewriteRuleToEnableUSAWhenNoTunnelBear(String fileName, String privacyRegime, String country, String region) {
		final List<File> configFiles = new ArrayList<File>();
		final File parentDir = new File(Constants.PATH_USER_HOME);
		parentDir.mkdirs();
		final File configFile = new File(parentDir, fileName);
		configFile.setWritable(true);

		// Create Charles config with header response rewrite for twc-privacy:exempt -> twc-privacy:gdpr
		CharlesConfiguration config = new CharlesConfiguration();
		//config.addRule(RewriteRuleType.MODIFY_HEADER, false, true, "twc-privacy", false, "exempt", false, false, false, "twc-privacy", false, "usa", false, RewriteRuleReplaceType.ONLY_FIRST);
		config.addRule(RewriteRuleType.MODIFY_HEADER, false, true, "twc-privacy", false, "[A-Za-z0-9\\.\\-]+", true, false, false, "twc-privacy", false, privacyRegime, false, RewriteRuleReplaceType.ONLY_FIRST);
		config.addRule(RewriteRuleType.MODIFY_HEADER, false, true, "twc-geoip-country", false, "[A-Za-z0-9\\.\\-]+", true, false, false, "twc-geoip-country", false, country, false, RewriteRuleReplaceType.ONLY_FIRST);
		//config.addRule(RewriteRuleType.MODIFY_HEADER, false, true, "twc-geoip-region", false, "GA", false, false, false, "twc-geoip-region", false, "GA", false, RewriteRuleReplaceType.ONLY_FIRST);
		config.addRule(RewriteRuleType.MODIFY_HEADER, false, true, "twc-geoip-region", false, "[A-Za-z0-9\\.\\-]+", true, false, false, "twc-geoip-region", false, region, false, RewriteRuleReplaceType.ONLY_FIRST);
		config.addLocation(Protocol.HTTPS, "dsx.weather.com", "", "/cms/*/privacy/en_US/twc-ios-flagship/*", "");

		config.saveConfigurations(fileName);


		return configFile;
	}
	
	/**
	 * Create a Charles configuration to rewrite privacy regime to the given regime (USA) values, region to WA (Washington)  when TB is not available
	 * @param fileName
	 *            - Name of file (.config extension) to store configuration. Will be created in user.dir
	 * @return Config Files (for deletion in After method)
	 */
	public File rewriteRuleToEnableUSAWhenNoTunnelBearAndroid(String fileName, String privacyRegime, String country, String region) {
		final List<File> configFiles = new ArrayList<File>();
		final File parentDir = new File(Constants.PATH_USER_HOME);
		parentDir.mkdirs();
		final File configFile = new File(parentDir, fileName);
		configFile.setWritable(true);

		// Create Charles config with header response rewrite for twc-privacy:exempt -> twc-privacy:gdpr
		CharlesConfiguration config = new CharlesConfiguration();
		//config.addRule(RewriteRuleType.MODIFY_HEADER, false, true, "twc-privacy", false, "exempt", false, false, false, "twc-privacy", false, "usa", false, RewriteRuleReplaceType.ONLY_FIRST);
		config.addRule(RewriteRuleType.MODIFY_HEADER, false, true, "twc-privacy", false, "[A-Za-z0-9\\.\\-]+", true, false, false, "twc-privacy", false, privacyRegime, false, RewriteRuleReplaceType.ONLY_FIRST);
		config.addRule(RewriteRuleType.MODIFY_HEADER, false, true, "twc-geoip-country", false, "[A-Za-z0-9\\.\\-]+", true, false, false, "twc-geoip-country", false, country, false, RewriteRuleReplaceType.ONLY_FIRST);
		//config.addRule(RewriteRuleType.MODIFY_HEADER, false, true, "twc-geoip-region", false, "GA", false, false, false, "twc-geoip-region", false, "GA", false, RewriteRuleReplaceType.ONLY_FIRST);
		config.addRule(RewriteRuleType.MODIFY_HEADER, false, true, "twc-geoip-region", false, "[A-Za-z0-9\\.\\-]+", true, false, false, "twc-geoip-region", false, region, false, RewriteRuleReplaceType.ONLY_FIRST);
		config.addLocation(Protocol.HTTPS, "dsx.weather.com", "", "/cms/*/privacy/en_US/*/*", "");

		config.saveConfigurations(fileName);


		return configFile;
	}
	
	/**
	 * Create a Charles configuration to rewrite privacy regime to the given regime (USA) values. 
	 * @param fileName
	 *            - Name of file (.config extension) to store configuration. Will be created in user.dir
	 * @return Config Files (for deletion in After method)
	 */
	public File rewriteRuleToEnableUSA(String fileName) {
		final List<File> configFiles = new ArrayList<File>();
		final File parentDir = new File(Constants.PATH_USER_HOME);
		parentDir.mkdirs();
		final File configFile = new File(parentDir, fileName);
		configFile.setWritable(true);

		// Create Charles config with header response rewrite for twc-privacy:exempt -> twc-privacy:gdpr
		CharlesConfiguration config = new CharlesConfiguration();
		//config.addRule(RewriteRuleType.MODIFY_HEADER, false, true, "twc-privacy", false, "exempt", false, false, false, "twc-privacy", false, "usa", false, RewriteRuleReplaceType.ONLY_FIRST);
		config.addRule(RewriteRuleType.MODIFY_HEADER, false, true, "twc-privacy", false, "[A-Za-z0-9\\.\\-]+", true, false, false, "twc-privacy", false, "usa", false, RewriteRuleReplaceType.ONLY_FIRST);
		config.addRule(RewriteRuleType.MODIFY_HEADER, false, true, "twc-geoip-country", false, "[A-Za-z0-9\\.\\-]+", true, false, false, "twc-geoip-country", false, "US", false, RewriteRuleReplaceType.ONLY_FIRST);
		//config.addRule(RewriteRuleType.MODIFY_HEADER, false, true, "twc-geoip-region", false, "GA", false, false, false, "twc-geoip-region", false, "GA", false, RewriteRuleReplaceType.ONLY_FIRST);
		config.addRule(RewriteRuleType.MODIFY_HEADER, false, true, "twc-geoip-region", false, "[A-Za-z0-9\\.\\-]+", true, false, false, "twc-geoip-region", false, "GA", false, RewriteRuleReplaceType.ONLY_FIRST);
		config.addLocation(Protocol.HTTPS, "dsx.weather.com", "", "/cms/*/privacy/en_US/twc-ios-flagship/*", "");

		config.saveConfigurations(fileName);


		return configFile;
	}
	
	/**
	 * Create a Charles configuration to rewrite privacy regime to the given regime (USA) values. 
	 * @param fileName
	 *            - Name of file (.config extension) to store configuration. Will be created in user.dir
	 * @return Config Files (for deletion in After method)
	 */
	public File rewriteRuleToEnableUSAAndroid(String fileName) {
		final List<File> configFiles = new ArrayList<File>();
		final File parentDir = new File(Constants.PATH_USER_HOME);
		parentDir.mkdirs();
		final File configFile = new File(parentDir, fileName);
		configFile.setWritable(true);

		// Create Charles config with header response rewrite for twc-privacy:exempt -> twc-privacy:gdpr
		CharlesConfiguration config = new CharlesConfiguration();
		//config.addRule(RewriteRuleType.MODIFY_HEADER, false, true, "twc-privacy", false, "exempt", false, false, false, "twc-privacy", false, "usa", false, RewriteRuleReplaceType.ONLY_FIRST);
		config.addRule(RewriteRuleType.MODIFY_HEADER, false, true, "twc-privacy", false, "[A-Za-z0-9\\.\\-]+", true, false, false, "twc-privacy", false, "usa", false, RewriteRuleReplaceType.ONLY_FIRST);
		config.addRule(RewriteRuleType.MODIFY_HEADER, false, true, "twc-geoip-country", false, "[A-Za-z0-9\\.\\-]+", true, false, false, "twc-geoip-country", false, "US", false, RewriteRuleReplaceType.ONLY_FIRST);
		//config.addRule(RewriteRuleType.MODIFY_HEADER, false, true, "twc-geoip-region", false, "GA", false, false, false, "twc-geoip-region", false, "GA", false, RewriteRuleReplaceType.ONLY_FIRST);
		config.addRule(RewriteRuleType.MODIFY_HEADER, false, true, "twc-geoip-region", false, "[A-Za-z0-9\\.\\-]+", true, false, false, "twc-geoip-region", false, "GA", false, RewriteRuleReplaceType.ONLY_FIRST);
		config.addLocation(Protocol.HTTPS, "dsx.weather.com", "", "/cms/*/privacy/en_US/*/*", "");

		config.saveConfigurations(fileName);


		return configFile;
	}
	
	/**
	 * Create a Charles configuration to rewrite privacy regime to the given regime (USACCPA) values. 
	 * @param fileName
	 *            - Name of file (.config extension) to store configuration. Will be created in user.dir
	 * @return Config Files (for deletion in After method)
	 */
	public File rewriteRuleToEnableUSACCPA(String fileName) {
		final List<File> configFiles = new ArrayList<File>();
		final File parentDir = new File(Constants.PATH_USER_HOME);
		parentDir.mkdirs();
		final File configFile = new File(parentDir, fileName);
		configFile.setWritable(true);

		// Create Charles config with header response rewrite for twc-privacy:exempt -> twc-privacy:gdpr
		CharlesConfiguration config = new CharlesConfiguration();
		//config.addRule(RewriteRuleType.MODIFY_HEADER, false, true, "twc-privacy", false, "exempt", false, false, false, "twc-privacy", false, "usa-ccpa", false, RewriteRuleReplaceType.ONLY_FIRST);
		config.addRule(RewriteRuleType.MODIFY_HEADER, false, true, "twc-privacy", false, "[A-Za-z0-9\\.\\-]+", true, false, false, "twc-privacy", false, "usa-ccpa", false, RewriteRuleReplaceType.ONLY_FIRST);
		config.addRule(RewriteRuleType.MODIFY_HEADER, false, true, "twc-geoip-country", false, "[A-Za-z0-9\\.\\-]+", true, false, false, "twc-geoip-country", false, "US", false, RewriteRuleReplaceType.ONLY_FIRST);
		//config.addRule(RewriteRuleType.MODIFY_HEADER, false, true, "twc-geoip-region", false, "GA", false, false, false, "twc-geoip-region", false, "CA", false, RewriteRuleReplaceType.ONLY_FIRST);
		config.addRule(RewriteRuleType.MODIFY_HEADER, false, true, "twc-geoip-region", false, "[A-Za-z0-9\\.\\-]+", true, false, false, "twc-geoip-region", false, "CA", false, RewriteRuleReplaceType.ONLY_FIRST);
		config.addLocation(Protocol.HTTPS, "dsx.weather.com", "", "/cms/*/privacy/en_US/twc-ios-flagship/*", "");

		config.saveConfigurations(fileName);


		return configFile;
	}
	
	/**
	 * Create a Charles configuration to rewrite privacy regime to the given regime (USACCPA) values. 
	 * @param fileName
	 *            - Name of file (.config extension) to store configuration. Will be created in user.dir
	 * @return Config Files (for deletion in After method)
	 */
	public File rewriteRuleToEnableUSACCPAAndroid(String fileName) {
		final List<File> configFiles = new ArrayList<File>();
		final File parentDir = new File(Constants.PATH_USER_HOME);
		parentDir.mkdirs();
		final File configFile = new File(parentDir, fileName);
		configFile.setWritable(true);

		// Create Charles config with header response rewrite for twc-privacy:exempt -> twc-privacy:gdpr
		CharlesConfiguration config = new CharlesConfiguration();
		//config.addRule(RewriteRuleType.MODIFY_HEADER, false, true, "twc-privacy", false, "exempt", false, false, false, "twc-privacy", false, "usa-ccpa", false, RewriteRuleReplaceType.ONLY_FIRST);
		config.addRule(RewriteRuleType.MODIFY_HEADER, false, true, "twc-privacy", false, "[A-Za-z0-9\\.\\-]+", true, false, false, "twc-privacy", false, "usa-ccpa", false, RewriteRuleReplaceType.ONLY_FIRST);
		config.addRule(RewriteRuleType.MODIFY_HEADER, false, true, "twc-geoip-country", false, "[A-Za-z0-9\\.\\-]+", true, false, false, "twc-geoip-country", false, "US", false, RewriteRuleReplaceType.ONLY_FIRST);
		//config.addRule(RewriteRuleType.MODIFY_HEADER, false, true, "twc-geoip-region", false, "GA", false, false, false, "twc-geoip-region", false, "CA", false, RewriteRuleReplaceType.ONLY_FIRST);
		config.addRule(RewriteRuleType.MODIFY_HEADER, false, true, "twc-geoip-region", false, "[A-Za-z0-9\\.\\-]+", true, false, false, "twc-geoip-region", false, "CA", false, RewriteRuleReplaceType.ONLY_FIRST);
		config.addLocation(Protocol.HTTPS, "dsx.weather.com", "", "/cms/*/privacy/en_US/*/*", "");

		config.saveConfigurations(fileName);


		return configFile;
	}
	
	/**
	 * Create a Charles configuration to rewrite privacy regime to the given regime (VIRGINIA) values. 
	 * @param fileName
	 *            - Name of file (.config extension) to store configuration. Will be created in user.dir
	 * @return Config Files (for deletion in After method)
	 */
	public File rewriteRuleToEnableVIRGINIA(String fileName) {
		final List<File> configFiles = new ArrayList<File>();
		final File parentDir = new File(Constants.PATH_USER_HOME);
		parentDir.mkdirs();
		final File configFile = new File(parentDir, fileName);
		configFile.setWritable(true);

		// Create Charles config with header response rewrite for twc-privacy:exempt -> twc-privacy:gdpr
		CharlesConfiguration config = new CharlesConfiguration();
		//config.addRule(RewriteRuleType.MODIFY_HEADER, false, true, "twc-privacy", false, "exempt", false, false, false, "twc-privacy", false, "usa-ccpa", false, RewriteRuleReplaceType.ONLY_FIRST);
		config.addRule(RewriteRuleType.MODIFY_HEADER, false, true, "twc-privacy", false, "[A-Za-z0-9\\.\\-]+", true, false, false, "twc-privacy", false, "usa-va", false, RewriteRuleReplaceType.ONLY_FIRST);
		config.addRule(RewriteRuleType.MODIFY_HEADER, false, true, "twc-geoip-country", false, "[A-Za-z0-9\\.\\-]+", true, false, false, "twc-geoip-country", false, "US", false, RewriteRuleReplaceType.ONLY_FIRST);
		//config.addRule(RewriteRuleType.MODIFY_HEADER, false, true, "twc-geoip-region", false, "GA", false, false, false, "twc-geoip-region", false, "CA", false, RewriteRuleReplaceType.ONLY_FIRST);
		config.addRule(RewriteRuleType.MODIFY_HEADER, false, true, "twc-geoip-region", false, "[A-Za-z0-9\\.\\-]+", true, false, false, "twc-geoip-region", false, "VA", false, RewriteRuleReplaceType.ONLY_FIRST);
		config.addLocation(Protocol.HTTPS, "dsx.weather.com", "", "/cms/*/privacy/en_US/twc-ios-flagship/*", "");

		config.saveConfigurations(fileName);


		return configFile;
	}
	
	/**
	 * Create a Charles configuration to rewrite privacy regime to the given regime (VIRGINIA) values. 
	 * @param fileName
	 *            - Name of file (.config extension) to store configuration. Will be created in user.dir
	 * @return Config Files (for deletion in After method)
	 */
	public File rewriteRuleToEnableVIRGINIAAndroid(String fileName) {
		final List<File> configFiles = new ArrayList<File>();
		final File parentDir = new File(Constants.PATH_USER_HOME);
		parentDir.mkdirs();
		final File configFile = new File(parentDir, fileName);
		configFile.setWritable(true);

		// Create Charles config with header response rewrite for twc-privacy:exempt -> twc-privacy:gdpr
		CharlesConfiguration config = new CharlesConfiguration();
		//config.addRule(RewriteRuleType.MODIFY_HEADER, false, true, "twc-privacy", false, "exempt", false, false, false, "twc-privacy", false, "usa-ccpa", false, RewriteRuleReplaceType.ONLY_FIRST);
		config.addRule(RewriteRuleType.MODIFY_HEADER, false, true, "twc-privacy", false, "[A-Za-z0-9\\.\\-]+", true, false, false, "twc-privacy", false, "usa-va", false, RewriteRuleReplaceType.ONLY_FIRST);
		config.addRule(RewriteRuleType.MODIFY_HEADER, false, true, "twc-geoip-country", false, "[A-Za-z0-9\\.\\-]+", true, false, false, "twc-geoip-country", false, "US", false, RewriteRuleReplaceType.ONLY_FIRST);
		//config.addRule(RewriteRuleType.MODIFY_HEADER, false, true, "twc-geoip-region", false, "GA", false, false, false, "twc-geoip-region", false, "CA", false, RewriteRuleReplaceType.ONLY_FIRST);
		config.addRule(RewriteRuleType.MODIFY_HEADER, false, true, "twc-geoip-region", false, "[A-Za-z0-9\\.\\-]+", true, false, false, "twc-geoip-region", false, "VA", false, RewriteRuleReplaceType.ONLY_FIRST);
		config.addLocation(Protocol.HTTPS, "dsx.weather.com", "", "/cms/*/privacy/en_US/*/*", "");

		config.saveConfigurations(fileName);


		return configFile;
	}
	
	
	/**
	 * Create a Charles configuration to rewrite privacy regime to the given regime (Latain America Peru) values. 
	 * @param fileName
	 *            - Name of file (.config extension) to store configuration. Will be created in user.dir
	 * @return Config Files (for deletion in After method)
	 */
	public File rewriteRuleToEnableLATAMPE(String fileName) {
		final List<File> configFiles = new ArrayList<File>();
		final File parentDir = new File(Constants.PATH_USER_HOME);
		parentDir.mkdirs();
		final File configFile = new File(parentDir, fileName);
		configFile.setWritable(true);

		// Create Charles config with header response rewrite for twc-privacy:exempt -> twc-privacy:gdpr
		CharlesConfiguration config = new CharlesConfiguration();
		//config.addRule(RewriteRuleType.MODIFY_HEADER, false, true, "twc-privacy", false, "exempt", false, false, false, "twc-privacy", false, "lgpd", false, RewriteRuleReplaceType.ONLY_FIRST);
		config.addRule(RewriteRuleType.MODIFY_HEADER, false, true, "twc-privacy", false, "[A-Za-z0-9\\.\\-]+", true, false, false, "twc-privacy", false, "latam-pe", false, RewriteRuleReplaceType.ONLY_FIRST);
		config.addRule(RewriteRuleType.MODIFY_HEADER, false, true, "twc-geoip-country", false, "[A-Za-z0-9\\.\\-]+", true, false, false, "twc-geoip-country", false, "PE", false, RewriteRuleReplaceType.ONLY_FIRST);
		config.addLocation(Protocol.HTTPS, "dsx.weather.com", "", "/cms/*/privacy/en_US/twc-ios-flagship/*", "");

		config.saveConfigurations(fileName);


		return configFile;
	}
	
	/**
	 * Create a Charles configuration to rewrite privacy regime to the given regime (Latain America Peru) values. 
	 * @param fileName
	 *            - Name of file (.config extension) to store configuration. Will be created in user.dir
	 * @return Config Files (for deletion in After method)
	 */
	public File rewriteRuleToEnableLATAMPEAndroid(String fileName) {
		final List<File> configFiles = new ArrayList<File>();
		final File parentDir = new File(Constants.PATH_USER_HOME);
		parentDir.mkdirs();
		final File configFile = new File(parentDir, fileName);
		configFile.setWritable(true);

		// Create Charles config with header response rewrite for twc-privacy:exempt -> twc-privacy:gdpr
		CharlesConfiguration config = new CharlesConfiguration();
		//config.addRule(RewriteRuleType.MODIFY_HEADER, false, true, "twc-privacy", false, "exempt", false, false, false, "twc-privacy", false, "lgpd", false, RewriteRuleReplaceType.ONLY_FIRST);
		config.addRule(RewriteRuleType.MODIFY_HEADER, false, true, "twc-privacy", false, "[A-Za-z0-9\\.\\-]+", true, false, false, "twc-privacy", false, "latam-pe", false, RewriteRuleReplaceType.ONLY_FIRST);
		config.addRule(RewriteRuleType.MODIFY_HEADER, false, true, "twc-geoip-country", false, "[A-Za-z0-9\\.\\-]+", true, false, false, "twc-geoip-country", false, "PE", false, RewriteRuleReplaceType.ONLY_FIRST);
		config.addLocation(Protocol.HTTPS, "dsx.weather.com", "", "/cms/*/privacy/en_US/*/*", "");

		config.saveConfigurations(fileName);


		return configFile;
	}
	
	/**
	 * Create a Charles configuration to rewrite privacy regime to the given regime (Latain America Dominican Republic) values. 
	 * @param fileName
	 *            - Name of file (.config extension) to store configuration. Will be created in user.dir
	 * @return Config Files (for deletion in After method)
	 */
	public File rewriteRuleToEnableLATAMDO(String fileName) {
		final List<File> configFiles = new ArrayList<File>();
		final File parentDir = new File(Constants.PATH_USER_HOME);
		parentDir.mkdirs();
		final File configFile = new File(parentDir, fileName);
		configFile.setWritable(true);

		// Create Charles config with header response rewrite for twc-privacy:exempt -> twc-privacy:gdpr
		CharlesConfiguration config = new CharlesConfiguration();
		//config.addRule(RewriteRuleType.MODIFY_HEADER, false, true, "twc-privacy", false, "exempt", false, false, false, "twc-privacy", false, "lgpd", false, RewriteRuleReplaceType.ONLY_FIRST);
		config.addRule(RewriteRuleType.MODIFY_HEADER, false, true, "twc-privacy", false, "[A-Za-z0-9\\.\\-]+", true, false, false, "twc-privacy", false, "latam-do", false, RewriteRuleReplaceType.ONLY_FIRST);
		config.addRule(RewriteRuleType.MODIFY_HEADER, false, true, "twc-geoip-country", false, "[A-Za-z0-9\\.\\-]+", true, false, false, "twc-geoip-country", false, "DO", false, RewriteRuleReplaceType.ONLY_FIRST);
		config.addLocation(Protocol.HTTPS, "dsx.weather.com", "", "/cms/*/privacy/en_US/twc-ios-flagship/*", "");

		config.saveConfigurations(fileName);


		return configFile;
	}
	
	/**
	 * Create a Charles configuration to rewrite privacy regime to the given regime (Latain America Dominican Republic) values. 
	 * @param fileName
	 *            - Name of file (.config extension) to store configuration. Will be created in user.dir
	 * @return Config Files (for deletion in After method)
	 */
	public File rewriteRuleToEnableLATAMDOAndroid(String fileName) {
		final List<File> configFiles = new ArrayList<File>();
		final File parentDir = new File(Constants.PATH_USER_HOME);
		parentDir.mkdirs();
		final File configFile = new File(parentDir, fileName);
		configFile.setWritable(true);

		// Create Charles config with header response rewrite for twc-privacy:exempt -> twc-privacy:gdpr
		CharlesConfiguration config = new CharlesConfiguration();
		//config.addRule(RewriteRuleType.MODIFY_HEADER, false, true, "twc-privacy", false, "exempt", false, false, false, "twc-privacy", false, "lgpd", false, RewriteRuleReplaceType.ONLY_FIRST);
		config.addRule(RewriteRuleType.MODIFY_HEADER, false, true, "twc-privacy", false, "[A-Za-z0-9\\.\\-]+", true, false, false, "twc-privacy", false, "latam-do", false, RewriteRuleReplaceType.ONLY_FIRST);
		config.addRule(RewriteRuleType.MODIFY_HEADER, false, true, "twc-geoip-country", false, "[A-Za-z0-9\\.\\-]+", true, false, false, "twc-geoip-country", false, "DO", false, RewriteRuleReplaceType.ONLY_FIRST);
		config.addLocation(Protocol.HTTPS, "dsx.weather.com", "", "/cms/*/privacy/en_US/*/*", "");

		config.saveConfigurations(fileName);


		return configFile;
	}
	
	/**
	 * Create a Charles configuration to rewrite privacy regime to the given regime (Latain America Colorado) values. 
	 * @param fileName
	 *            - Name of file (.config extension) to store configuration. Will be created in user.dir
	 * @return Config Files (for deletion in After method)
	 */
	public File rewriteRuleToEnableLATAMCO(String fileName) {
		final List<File> configFiles = new ArrayList<File>();
		final File parentDir = new File(Constants.PATH_USER_HOME);
		parentDir.mkdirs();
		final File configFile = new File(parentDir, fileName);
		configFile.setWritable(true);

		// Create Charles config with header response rewrite for twc-privacy:exempt -> twc-privacy:gdpr
		CharlesConfiguration config = new CharlesConfiguration();
		//config.addRule(RewriteRuleType.MODIFY_HEADER, false, true, "twc-privacy", false, "exempt", false, false, false, "twc-privacy", false, "lgpd", false, RewriteRuleReplaceType.ONLY_FIRST);
		config.addRule(RewriteRuleType.MODIFY_HEADER, false, true, "twc-privacy", false, "[A-Za-z0-9\\.\\-]+", true, false, false, "twc-privacy", false, "latam-co", false, RewriteRuleReplaceType.ONLY_FIRST);
		config.addRule(RewriteRuleType.MODIFY_HEADER, false, true, "twc-geoip-country", false, "[A-Za-z0-9\\.\\-]+", true, false, false, "twc-geoip-country", false, "CO", false, RewriteRuleReplaceType.ONLY_FIRST);
		config.addLocation(Protocol.HTTPS, "dsx.weather.com", "", "/cms/*/privacy/en_US/twc-ios-flagship/*", "");

		config.saveConfigurations(fileName);


		return configFile;
	}
	
	/**
	 * Create a Charles configuration to rewrite privacy regime to the given regime (Latain America Colorado) values. 
	 * @param fileName
	 *            - Name of file (.config extension) to store configuration. Will be created in user.dir
	 * @return Config Files (for deletion in After method)
	 */
	public File rewriteRuleToEnableLATAMCOAndroid(String fileName) {
		final List<File> configFiles = new ArrayList<File>();
		final File parentDir = new File(Constants.PATH_USER_HOME);
		parentDir.mkdirs();
		final File configFile = new File(parentDir, fileName);
		configFile.setWritable(true);

		// Create Charles config with header response rewrite for twc-privacy:exempt -> twc-privacy:gdpr
		CharlesConfiguration config = new CharlesConfiguration();
		//config.addRule(RewriteRuleType.MODIFY_HEADER, false, true, "twc-privacy", false, "exempt", false, false, false, "twc-privacy", false, "lgpd", false, RewriteRuleReplaceType.ONLY_FIRST);
		config.addRule(RewriteRuleType.MODIFY_HEADER, false, true, "twc-privacy", false, "[A-Za-z0-9\\.\\-]+", true, false, false, "twc-privacy", false, "latam-co", false, RewriteRuleReplaceType.ONLY_FIRST);
		config.addRule(RewriteRuleType.MODIFY_HEADER, false, true, "twc-geoip-country", false, "[A-Za-z0-9\\.\\-]+", true, false, false, "twc-geoip-country", false, "CO", false, RewriteRuleReplaceType.ONLY_FIRST);
		config.addLocation(Protocol.HTTPS, "dsx.weather.com", "", "/cms/*/privacy/en_US/*/*", "");

		config.saveConfigurations(fileName);


		return configFile;
	}
	
	/**
	 * Create a Charles configuration to rewrite privacy regime to the given regime (EXEMPT) values. 
	 * @param fileName
	 *            - Name of file (.config extension) to store configuration. Will be created in user.dir
	 * @return Config Files (for deletion in After method)
	 */
	public File rewriteRuleToEnableEXEMPT(String fileName) {
		final List<File> configFiles = new ArrayList<File>();
		final File parentDir = new File(Constants.PATH_USER_HOME);
		parentDir.mkdirs();
		final File configFile = new File(parentDir, fileName);
		configFile.setWritable(true);

		// Create Charles config with header response rewrite for twc-privacy:exempt -> twc-privacy:gdpr
		CharlesConfiguration config = new CharlesConfiguration();
		//config.addRule(RewriteRuleType.MODIFY_HEADER, false, true, "twc-privacy", false, "exempt", false, false, false, "twc-privacy", false, "lgpd", false, RewriteRuleReplaceType.ONLY_FIRST);
		config.addRule(RewriteRuleType.MODIFY_HEADER, false, true, "twc-privacy", false, "[A-Za-z0-9\\.\\-]+", true, false, false, "twc-privacy", false, "exempt", false, RewriteRuleReplaceType.ONLY_FIRST);
		config.addRule(RewriteRuleType.MODIFY_HEADER, false, true, "twc-geoip-country", false, "[A-Za-z0-9\\.\\-]+", true, false, false, "twc-geoip-country", false, "CA", false, RewriteRuleReplaceType.ONLY_FIRST);
		//config.addRule(RewriteRuleType.MODIFY_HEADER, false, true, "twc-geoip-region", false, "[A-Za-z0-9\\.\\-]+", false, false, false, "twc-geoip-region", false, "ON", false, RewriteRuleReplaceType.ONLY_FIRST);
		config.addLocation(Protocol.HTTPS, "dsx.weather.com", "", "/cms/*/privacy/en_US/twc-ios-flagship/*", "");

		config.saveConfigurations(fileName);


		return configFile;
	}
	
	/**
	 * Create a Charles configuration to rewrite privacy regime to the given regime (EXEMPT) values. 
	 * @param fileName
	 *            - Name of file (.config extension) to store configuration. Will be created in user.dir
	 * @return Config Files (for deletion in After method)
	 */
	public File rewriteRuleToEnableEXEMPTAndroid(String fileName) {
		final List<File> configFiles = new ArrayList<File>();
		final File parentDir = new File(Constants.PATH_USER_HOME);
		parentDir.mkdirs();
		final File configFile = new File(parentDir, fileName);
		configFile.setWritable(true);

		// Create Charles config with header response rewrite for twc-privacy:exempt -> twc-privacy:gdpr
		CharlesConfiguration config = new CharlesConfiguration();
		//config.addRule(RewriteRuleType.MODIFY_HEADER, false, true, "twc-privacy", false, "exempt", false, false, false, "twc-privacy", false, "lgpd", false, RewriteRuleReplaceType.ONLY_FIRST);
		config.addRule(RewriteRuleType.MODIFY_HEADER, false, true, "twc-privacy", false, "[A-Za-z0-9\\.\\-]+", true, false, false, "twc-privacy", false, "exempt", false, RewriteRuleReplaceType.ONLY_FIRST);
		config.addRule(RewriteRuleType.MODIFY_HEADER, false, true, "twc-geoip-country", false, "[A-Za-z0-9\\.\\-]+", true, false, false, "twc-geoip-country", false, "CA", false, RewriteRuleReplaceType.ONLY_FIRST);
		//config.addRule(RewriteRuleType.MODIFY_HEADER, false, true, "twc-geoip-region", false, "[A-Za-z0-9\\.\\-]+", false, false, false, "twc-geoip-region", false, "ON", false, RewriteRuleReplaceType.ONLY_FIRST);
		config.addLocation(Protocol.HTTPS, "dsx.weather.com", "", "/cms/*/privacy/en_US/*/*", "");

		config.saveConfigurations(fileName);


		return configFile;
	}
	
	/**
	 * Create a Charles configuration to rewrite privacy regime to the given regime (CHINA) values. 
	 * @param fileName
	 *            - Name of file (.config extension) to store configuration. Will be created in user.dir
	 * @return Config Files (for deletion in After method)
	 */
	public File rewriteRuleToEnableCHINA(String fileName) {
		final List<File> configFiles = new ArrayList<File>();
		final File parentDir = new File(Constants.PATH_USER_HOME);
		parentDir.mkdirs();
		final File configFile = new File(parentDir, fileName);
		configFile.setWritable(true);

		// Create Charles config with header response rewrite for twc-privacy:exempt -> twc-privacy:gdpr
		CharlesConfiguration config = new CharlesConfiguration();
		
		config.addRule(RewriteRuleType.ADD_HEADER, true, false, "", false, "", false, false, false, "X-Forwarded-For", false, "221.192.199.49", false, RewriteRuleReplaceType.ALL);
		config.addLocation(Protocol.HTTPS, "dsx.weather.com", "", "/cms/*/privacy/en_US/twc-ios-flagship/*", "");

		config.saveConfigurations(fileName);


		return configFile;
	}
	
	/**
	 * Create a Charles configuration to rewrite privacy regime to the given regime (CHINA) values. 
	 * @param fileName
	 *            - Name of file (.config extension) to store configuration. Will be created in user.dir
	 * @return Config Files (for deletion in After method)
	 */
	public File rewriteRuleToEnableCHINAAndroid(String fileName) {
		final List<File> configFiles = new ArrayList<File>();
		final File parentDir = new File(Constants.PATH_USER_HOME);
		parentDir.mkdirs();
		final File configFile = new File(parentDir, fileName);
		configFile.setWritable(true);

		// Create Charles config with header response rewrite for twc-privacy:exempt -> twc-privacy:gdpr
		CharlesConfiguration config = new CharlesConfiguration();
		
		config.addRule(RewriteRuleType.ADD_HEADER, true, false, "", false, "", false, false, false, "X-Forwarded-For", false, "221.192.199.49", false, RewriteRuleReplaceType.ALL);
		config.addLocation(Protocol.HTTPS, "dsx.weather.com", "", "/cms/*/privacy/en_US/*/*", "");

		config.saveConfigurations(fileName);


		return configFile;
	}
	
	/**
	 * Create a Charles configuration to rewrite privacy regime to the given regime (RUSSIA) values. 
	 * @param fileName
	 *            - Name of file (.config extension) to store configuration. Will be created in user.dir
	 * @return Config Files (for deletion in After method)
	 */
	public File rewriteRuleToEnableRUSSIA(String fileName) {
		final List<File> configFiles = new ArrayList<File>();
		final File parentDir = new File(Constants.PATH_USER_HOME);
		parentDir.mkdirs();
		final File configFile = new File(parentDir, fileName);
		configFile.setWritable(true);

		// Create Charles config with header response rewrite for twc-privacy:exempt -> twc-privacy:gdpr
		CharlesConfiguration config = new CharlesConfiguration();
		config.addRule(RewriteRuleType.MODIFY_HEADER, false, true, "twc-geoip-country", false, "[A-Za-z0-9\\.\\-]+", true, false, false, "twc-geoip-country", false, "RU", false, RewriteRuleReplaceType.ONLY_FIRST);
		
		config.addLocation(Protocol.HTTPS, "dsx.weather.com", "", "/cms/*/privacy/en_US/twc-ios-flagship/*", "");

		config.saveConfigurations(fileName);


		return configFile;
	}
	
	/**
	 * Create a Charles configuration to rewrite privacy regime to the given regime (RUSSIA) values. 
	 * @param fileName
	 *            - Name of file (.config extension) to store configuration. Will be created in user.dir
	 * @return Config Files (for deletion in After method)
	 */
	public File rewriteRuleToEnableRUSSIAAndroid(String fileName) {
		final List<File> configFiles = new ArrayList<File>();
		final File parentDir = new File(Constants.PATH_USER_HOME);
		parentDir.mkdirs();
		final File configFile = new File(parentDir, fileName);
		configFile.setWritable(true);

		// Create Charles config with header response rewrite for twc-privacy:exempt -> twc-privacy:gdpr
		CharlesConfiguration config = new CharlesConfiguration();
		config.addRule(RewriteRuleType.MODIFY_HEADER, false, true, "twc-geoip-country", false, "[A-Za-z0-9\\.\\-]+", true, false, false, "twc-geoip-country", false, "RU", false, RewriteRuleReplaceType.ONLY_FIRST);
		
		config.addLocation(Protocol.HTTPS, "dsx.weather.com", "", "/cms/*/privacy/en_US/*/*", "");

		config.saveConfigurations(fileName);


		return configFile;
	}
	
	/**
	 * Create a Charles configuration to rewrite privacy regime to the given regime (BELARUS) values. 
	 * @param fileName
	 *            - Name of file (.config extension) to store configuration. Will be created in user.dir
	 * @return Config Files (for deletion in After method)
	 */
	public File rewriteRuleToEnableBELARUS(String fileName) {
		final List<File> configFiles = new ArrayList<File>();
		final File parentDir = new File(Constants.PATH_USER_HOME);
		parentDir.mkdirs();
		final File configFile = new File(parentDir, fileName);
		configFile.setWritable(true);

		// Create Charles config with header response rewrite for twc-privacy:exempt -> twc-privacy:gdpr
		CharlesConfiguration config = new CharlesConfiguration();
		config.addRule(RewriteRuleType.MODIFY_HEADER, false, true, "twc-geoip-country", false, "[A-Za-z0-9\\.\\-]+", true, false, false, "twc-geoip-country", false, "BY", false, RewriteRuleReplaceType.ONLY_FIRST);
		
		config.addLocation(Protocol.HTTPS, "dsx.weather.com", "", "/cms/*/privacy/en_US/twc-ios-flagship/*", "");

		config.saveConfigurations(fileName);


		return configFile;
	}
	
	/**
	 * Create a Charles configuration to rewrite privacy regime to the given regime (BELARUS) values. 
	 * @param fileName
	 *            - Name of file (.config extension) to store configuration. Will be created in user.dir
	 * @return Config Files (for deletion in After method)
	 */
	public File rewriteRuleToEnableBELARUSAndroid(String fileName) {
		final List<File> configFiles = new ArrayList<File>();
		final File parentDir = new File(Constants.PATH_USER_HOME);
		parentDir.mkdirs();
		final File configFile = new File(parentDir, fileName);
		configFile.setWritable(true);

		// Create Charles config with header response rewrite for twc-privacy:exempt -> twc-privacy:gdpr
		CharlesConfiguration config = new CharlesConfiguration();
		config.addRule(RewriteRuleType.MODIFY_HEADER, false, true, "twc-geoip-country", false, "[A-Za-z0-9\\.\\-]+", true, false, false, "twc-geoip-country", false, "BY", false, RewriteRuleReplaceType.ONLY_FIRST);
		
		config.addLocation(Protocol.HTTPS, "dsx.weather.com", "", "/cms/*/privacy/en_US/*/*", "");

		config.saveConfigurations(fileName);


		return configFile;
	}
	
	/**
	 * Create a Charles configuration to rewrite vt1ContentMode mode to the given content mode. Rewrite to severe1 or severe2 to show Breaking
	 * News/Trending module
	 *
	 * @param fileName
	 *            - Name of file (.config extension) to store configuration. Will be created in user.dir
	 * @param contentMode
	 *            - What to change the content mode to
	 * @return Config Files (for deletion in After method)
	 */
	public File changeVt1ContentMode(String fileName, String contentMode) {
		final List<File> configFiles = new ArrayList<File>();
		final File parentDir = new File(Constants.PATH_USER_HOME);
		parentDir.mkdirs();
		final File configFile = new File(parentDir, fileName);
		configFile.setWritable(true);

		// Create Charles and configure rewrite to enable Breaking News
		 CharlesConfiguration config = new CharlesConfiguration();

		 config.addLocation(Protocol.HTTPS, "api.weather.com", "", "/v3/aggcommon/" + "*vt1contentMode*", "*");
		 config.addRule(RewriteRuleType.BODY, false, true, "", false, "\"mode\"\\s*:\\s*\"[A-Za-z0-9]*\"", true, false, false, "", false, "\"mode\" : \"" + contentMode + "\"", false, RewriteRuleReplaceType.ONLY_FIRST);
		 config.saveConfigurations(fileName);

		return configFile;
	}
	
	/**
	 * Create a Charles configuration to rewrite vt1ContentMode mode to the given content mode. Rewrite to severe1 or severe2 to show Breaking
	 * News/Trending module
	 *
	 * @param fileName
	 *            - Name of file (.config extension) to store configuration. Will be created in user.dir
	 * @param contentMode
	 *            - What to change the content mode to
	 * @return Config Files (for deletion in After method)
	 */
	public File changeVt1ContentModeWhenNoTunnelBear(String fileName, String contentMode, String privacyRegime, String country, String region) {
		final List<File> configFiles = new ArrayList<File>();
		final File parentDir = new File(Constants.PATH_USER_HOME);
		parentDir.mkdirs();
		final File configFile = new File(parentDir, fileName);
		configFile.setWritable(true);

		// Create Charles and configure rewrite to enable Breaking News
		 CharlesConfiguration config = new CharlesConfiguration();

		 config.addLocation(Protocol.HTTPS, "api.weather.com", "", "/v3/aggcommon/" + "*vt1contentMode*", "*");
		 config.addRule(RewriteRuleType.BODY, false, true, "", false, "\"mode\"\\s*:\\s*\"[A-Za-z0-9]*\"", true, false, false, "", false, "\"mode\" : \"" + contentMode + "\"", false, RewriteRuleReplaceType.ONLY_FIRST);
		 config.saveConfigurations(fileName);

		//config.addRule(RewriteRuleType.MODIFY_HEADER, false, true, "twc-privacy", false, "exempt", false, false, false, "twc-privacy", false, "usa", false, RewriteRuleReplaceType.ONLY_FIRST);
		config.addRule(RewriteRuleType.MODIFY_HEADER, false, true, "twc-privacy", false, "[A-Za-z0-9\\.\\-]+", true, false, false, "twc-privacy", false, privacyRegime, false, RewriteRuleReplaceType.ONLY_FIRST);
		config.addRule(RewriteRuleType.MODIFY_HEADER, false, true, "twc-geoip-country", false, "[A-Za-z0-9\\.\\-]+", true, false, false, "twc-geoip-country", false, country, false, RewriteRuleReplaceType.ONLY_FIRST);
		//config.addRule(RewriteRuleType.MODIFY_HEADER, false, true, "twc-geoip-region", false, "GA", false, false, false, "twc-geoip-region", false, "GA", false, RewriteRuleReplaceType.ONLY_FIRST);
		config.addRule(RewriteRuleType.MODIFY_HEADER, false, true, "twc-geoip-region", false, "[A-Za-z0-9\\.\\-]+", true, false, false, "twc-geoip-region", false, region, false, RewriteRuleReplaceType.ONLY_FIRST);
		config.addLocation(Protocol.HTTPS, "dsx.weather.com", "", "/cms/*/privacy/en_US/twc-ios-flagship/*", "");

		config.saveConfigurations(fileName);


		return configFile;
	}
	
	/**
	 * Create a Charles configuration to rewrite vt1ContentMode mode to the given content mode. Rewrite to severe1 or severe2 to show Breaking
	 * News/Trending module
	 *
	 * @param fileName
	 *            - Name of file (.config extension) to store configuration. Will be created in user.dir
	 * @param contentMode
	 *            - What to change the content mode to
	 * @return Config Files (for deletion in After method)
	 */
	public File changeVt1ContentModeWhenNoTunnelBearAndroid(String fileName, String contentMode, String privacyRegime, String country, String region) {
		final List<File> configFiles = new ArrayList<File>();
		final File parentDir = new File(Constants.PATH_USER_HOME);
		parentDir.mkdirs();
		final File configFile = new File(parentDir, fileName);
		configFile.setWritable(true);

		// Create Charles and configure rewrite to enable Breaking News
		 CharlesConfiguration config = new CharlesConfiguration();

		 config.addLocation(Protocol.HTTPS, "api.weather.com", "", "/v3/aggcommon/" + "*vt1contentMode*", "*");
		 config.addRule(RewriteRuleType.BODY, false, true, "", false, "\"mode\"\\s*:\\s*\"[A-Za-z0-9]*\"", true, false, false, "", false, "\"mode\" : \"" + contentMode + "\"", false, RewriteRuleReplaceType.ONLY_FIRST);
		 config.saveConfigurations(fileName);

		//config.addRule(RewriteRuleType.MODIFY_HEADER, false, true, "twc-privacy", false, "exempt", false, false, false, "twc-privacy", false, "usa", false, RewriteRuleReplaceType.ONLY_FIRST);
		config.addRule(RewriteRuleType.MODIFY_HEADER, false, true, "twc-privacy", false, "[A-Za-z0-9\\.\\-]+", true, false, false, "twc-privacy", false, privacyRegime, false, RewriteRuleReplaceType.ONLY_FIRST);
		config.addRule(RewriteRuleType.MODIFY_HEADER, false, true, "twc-geoip-country", false, "[A-Za-z0-9\\.\\-]+", true, false, false, "twc-geoip-country", false, country, false, RewriteRuleReplaceType.ONLY_FIRST);
		//config.addRule(RewriteRuleType.MODIFY_HEADER, false, true, "twc-geoip-region", false, "GA", false, false, false, "twc-geoip-region", false, "GA", false, RewriteRuleReplaceType.ONLY_FIRST);
		config.addRule(RewriteRuleType.MODIFY_HEADER, false, true, "twc-geoip-region", false, "[A-Za-z0-9\\.\\-]+", true, false, false, "twc-geoip-region", false, region, false, RewriteRuleReplaceType.ONLY_FIRST);
		config.addLocation(Protocol.HTTPS, "dsx.weather.com", "", "/cms/*/privacy/en_US/*/*", "");

		config.saveConfigurations(fileName);


		return configFile;
	}
	
	/**
	 * Create a Charles configuration to rewrite the Geo IP Country to given Country. 
	 * @param fileName
	 *            - Name of file (.config extension) to store configuration. Will be created in user.dir
	 * @return Config Files (for deletion in After method)
	 */
	public File rewriteRuleToOverrideGeoIpCountry(String fileName, String country) {
		final List<File> configFiles = new ArrayList<File>();
		final File parentDir = new File(Constants.PATH_USER_HOME);
		parentDir.mkdirs();
		final File configFile = new File(parentDir, fileName);
		configFile.setWritable(true);

		// Create Charles config with header response rewrite geoipcountry to given Country as a parameter
		CharlesConfiguration config = new CharlesConfiguration();
		config.addRule(RewriteRuleType.MODIFY_HEADER, false, true, "twc-geoip-country", false, "[A-Za-z0-9\\.\\-]+", true, false, false, "twc-geoip-country", false, country, false, RewriteRuleReplaceType.ONLY_FIRST);
		config.saveConfigurations(fileName);
		return configFile;
	}
	
	public File charlesGeneralConfigFile(String fileName) {
		final List<File> configFiles = new ArrayList<File>();
		final File parentDir = new File(Constants.PATH_USER_HOME);
		parentDir.mkdirs();
		final File configFile = new File(parentDir, fileName);
		configFile.setWritable(true);

		// Create Charles config with header response rewrite for twc-privacy:exempt -> twc-privacy:gdpr
		CharlesConfiguration config = new CharlesConfiguration();
		

		config.saveConfigurations(fileName);


		return configFile;
	}
	
	/**
	 * Create a Charles configuration by enable maplocal to get Severe Insight Card 
	 * @param fileName
	 * @param jsonPath
	 */
	 public void mapLocalForSevereInsight(String fileName, String jsonPath) {
	        //String jsonPath = "src/test/resources/SevereInsightAtlanta.json";
	        try {
	            mapConfig = new CharlesConfiguration();
	            mapConfig.addMapping(CharlesConfiguration.Protocol.HTTPS, "api.weather.com", "", "/v3/aggcommon/*", "*",
	                    jsonPath, false);
	            mapConfig.saveConfigurations(fileName);
	           // proxy = new CharlesProxy("localhost", CONFIG_FILE_PATH);
	        } catch (CharlesProxyException e) {
	            Assert.fail(e.getMessage());
	        }

	        //proxy.startCharlesProxy();
	        //proxy.enableMapLocal();
	 }
	 
		/**
		 * Create a Charles configuration by enable maplocal to get Severe Insight Card
		 * 
		 * @param fileName
		 * @param jsonPath
		 */
		public void mapLocalForSevereInsightWhenNoTunnelBear(String fileName, String jsonPath, String privacyRegime,
				String country, String region) {
			// String jsonPath = "src/test/resources/SevereInsightAtlanta.json";
			try {
				CharlesConfiguration config = new CharlesConfiguration();

				// config.addRule(RewriteRuleType.MODIFY_HEADER, false, true, "twc-privacy",
				// false, "exempt", false, false, false, "twc-privacy", false, "usa", false,
				// RewriteRuleReplaceType.ONLY_FIRST);
				config.addRule(RewriteRuleType.MODIFY_HEADER, false, true, "twc-privacy", false, "[A-Za-z0-9\\.\\-]+",
						true, false, false, "twc-privacy", false, privacyRegime, false,
						RewriteRuleReplaceType.ONLY_FIRST);
				config.addRule(RewriteRuleType.MODIFY_HEADER, false, true, "twc-geoip-country", false,
						"[A-Za-z0-9\\.\\-]+", true, false, false, "twc-geoip-country", false, country, false,
						RewriteRuleReplaceType.ONLY_FIRST);
				// config.addRule(RewriteRuleType.MODIFY_HEADER, false, true,
				// "twc-geoip-region", false, "GA", false, false, false, "twc-geoip-region",
				// false, "GA", false, RewriteRuleReplaceType.ONLY_FIRST);
				config.addRule(RewriteRuleType.MODIFY_HEADER, false, true, "twc-geoip-region", false,
						"[A-Za-z0-9\\.\\-]+", true, false, false, "twc-geoip-region", false, region, false,
						RewriteRuleReplaceType.ONLY_FIRST);
				config.addLocation(Protocol.HTTPS, "dsx.weather.com", "", "/cms/*/privacy/en_US/twc-ios-flagship/*",
						"");

				config.saveConfigurations(fileName);

				config.addMapping(CharlesConfiguration.Protocol.HTTPS, "api.weather.com", "", "/v3/aggcommon/*", "*",
						jsonPath, false);
				config.saveConfigurations(fileName);

				// proxy = new CharlesProxy("localhost", CONFIG_FILE_PATH);
			} catch (CharlesProxyException e) {
				Assert.fail(e.getMessage());
			}

			// proxy.startCharlesProxy();
			// proxy.enableMapLocal();
		}
		 
			/**
			 * Create a Charles configuration by enable maplocal to get Severe Insight Card
			 * 
			 * @param fileName
			 * @param jsonPath
			 */
			public void mapLocalForSevereInsightWhenNoTunnelBearAndroid(String fileName, String jsonPath,
					String privacyRegime, String country, String region) {
				// String jsonPath = "src/test/resources/SevereInsightAtlanta.json";
				try {
					CharlesConfiguration config = new CharlesConfiguration();

					// config.addRule(RewriteRuleType.MODIFY_HEADER, false, true, "twc-privacy",
					// false, "exempt", false, false, false, "twc-privacy", false, "usa", false,
					// RewriteRuleReplaceType.ONLY_FIRST);
					config.addRule(RewriteRuleType.MODIFY_HEADER, false, true, "twc-privacy", false,
							"[A-Za-z0-9\\.\\-]+", true, false, false, "twc-privacy", false, privacyRegime, false,
							RewriteRuleReplaceType.ONLY_FIRST);
					config.addRule(RewriteRuleType.MODIFY_HEADER, false, true, "twc-geoip-country", false,
							"[A-Za-z0-9\\.\\-]+", true, false, false, "twc-geoip-country", false, country, false,
							RewriteRuleReplaceType.ONLY_FIRST);
					// config.addRule(RewriteRuleType.MODIFY_HEADER, false, true,
					// "twc-geoip-region", false, "GA", false, false, false, "twc-geoip-region",
					// false, "GA", false, RewriteRuleReplaceType.ONLY_FIRST);
					config.addRule(RewriteRuleType.MODIFY_HEADER, false, true, "twc-geoip-region", false,
							"[A-Za-z0-9\\.\\-]+", true, false, false, "twc-geoip-region", false, region, false,
							RewriteRuleReplaceType.ONLY_FIRST);
					config.addLocation(Protocol.HTTPS, "dsx.weather.com", "", "/cms/*/privacy/en_US/*/*", "");

					config.saveConfigurations(fileName);

					config.addMapping(CharlesConfiguration.Protocol.HTTPS, "api.weather.com", "", "/v3/aggcommon/*",
							"*", jsonPath, false);
					config.saveConfigurations(fileName);

					// proxy = new CharlesProxy("localhost", CONFIG_FILE_PATH);
				} catch (CharlesProxyException e) {
					Assert.fail(e.getMessage());
				}

				// proxy.startCharlesProxy();
				// proxy.enableMapLocal();
			}
	 
	 public void mapLocalForEditorialVideoHeadLineCard(String fileName, String jsonPath) {
	        //String jsonPath = "src/test/resources/SevereInsightAtlanta.json";
	        try {
	            mapConfig = new CharlesConfiguration();
	            mapConfig.addMapping(CharlesConfiguration.Protocol.HTTPS, "dsx.weather.com", "", "/cms/v4/list/en_US/ios/video/app", "*",
	                    jsonPath, false);
	            mapConfig.saveConfigurations(fileName);
	            //proxy = new CharlesProxy("localhost", CONFIG_FILE_PATH);
	        } catch (CharlesProxyException e) {
	            Assert.fail(e.getMessage());
	        }

	        //proxy.startCharlesProxy();
	        //proxy.enableMapLocal();
	 }
	 
	 public void reWriteContentModeAndMapLocalForEditorialVideoHeadLineCard(String fileName, String jsonPath, String contentMode) {
	        //String jsonPath = "src/test/resources/SevereInsightAtlanta.json";
	        try {
	        	/*final List<File> configFiles = new ArrayList<File>();
	    		final File parentDir = new File(Constants.PATH_USER_HOME);
	    		parentDir.mkdirs();
	    		final File configFile = new File(parentDir, fileName);
	    		configFile.setWritable(true);*/

	    		// Create Charles and configure rewrite to enable Breaking News
	    		 CharlesConfiguration config = new CharlesConfiguration();

	    		 config.addLocation(Protocol.HTTPS, "api.weather.com", "", "/v3/aggcommon/" + "*vt1contentMode*", "*");
	    		 config.addRule(RewriteRuleType.BODY, false, true, "", false, "\"mode\"\\s*:\\s*\"[A-Za-z0-9]*\"", true, false, false, "", false, "\"mode\" : \"" + contentMode + "\"", false, RewriteRuleReplaceType.ONLY_FIRST);
	    		 config.saveConfigurations(fileName);

	    		//return configFile;
	    		
	    		
	            //mapConfig = new CharlesConfiguration();
	    		 config.addMapping(CharlesConfiguration.Protocol.HTTPS, "dsx.weather.com", "", "/cms/v4/list/en_US/ios/video/app", "*",
	                    jsonPath, false);
	    		 config.saveConfigurations(fileName);
	            //proxy = new CharlesProxy("localhost", CONFIG_FILE_PATH);
	        } catch (CharlesProxyException e) {
	            Assert.fail(e.getMessage());
	        }

	        //proxy.startCharlesProxy();
	        //proxy.enableMapLocal();
	 }
	 
	 public void reWriteContentModeAndMapLocalForEditorialVideoHeadLineCardAndroid(String fileName, String jsonPath, String contentMode) {
	        //String jsonPath = "src/test/resources/SevereInsightAtlanta.json";
	        try {
	        	/*final List<File> configFiles = new ArrayList<File>();
	    		final File parentDir = new File(Constants.PATH_USER_HOME);
	    		parentDir.mkdirs();
	    		final File configFile = new File(parentDir, fileName);
	    		configFile.setWritable(true);*/

	    		// Create Charles and configure rewrite to enable Breaking News
	    		 CharlesConfiguration config = new CharlesConfiguration();

	    		 config.addLocation(Protocol.HTTPS, "api.weather.com", "", "/v3/aggcommon/" + "*vt1contentMode*", "*");
	    		 config.addRule(RewriteRuleType.BODY, false, true, "", false, "\"mode\"\\s*:\\s*\"[A-Za-z0-9]*\"", true, false, false, "", false, "\"mode\" : \"" + contentMode + "\"", false, RewriteRuleReplaceType.ONLY_FIRST);
	    		 config.saveConfigurations(fileName);

	    		//return configFile;
	    		
	    		
	            //mapConfig = new CharlesConfiguration();
	    		 config.addMapping(CharlesConfiguration.Protocol.HTTPS, "dsx.weather.com", "", "/cms/v4/list/en_US/mobile/video/app", "*",
	                    jsonPath, false);
	    		 config.saveConfigurations(fileName);
	            //proxy = new CharlesProxy("localhost", CONFIG_FILE_PATH);
	        } catch (CharlesProxyException e) {
	            Assert.fail(e.getMessage());
	        }

	        //proxy.startCharlesProxy();
	        //proxy.enableMapLocal();
	 }
	 
	 public void reWriteContentModeAndMapLocalForEditorialVideoHeadLineCardWhenNoTunnelBear(String fileName, String jsonPath, String contentMode, String privacyRegime, String country, String region) {
	        //String jsonPath = "src/test/resources/SevereInsightAtlanta.json";
	        try {
	        	/*final List<File> configFiles = new ArrayList<File>();
	    		final File parentDir = new File(Constants.PATH_USER_HOME);
	    		parentDir.mkdirs();
	    		final File configFile = new File(parentDir, fileName);
	    		configFile.setWritable(true);*/

	    		// Create Charles and configure rewrite to enable Breaking News
	    		 CharlesConfiguration config = new CharlesConfiguration();

	    		 config.addLocation(Protocol.HTTPS, "api.weather.com", "", "/v3/aggcommon/" + "*vt1contentMode*", "*");
	    		 config.addRule(RewriteRuleType.BODY, false, true, "", false, "\"mode\"\\s*:\\s*\"[A-Za-z0-9]*\"", true, false, false, "", false, "\"mode\" : \"" + contentMode + "\"", false, RewriteRuleReplaceType.ONLY_FIRST);
	    		 config.saveConfigurations(fileName);

	    		//return configFile;
	    		
	    		//config.addRule(RewriteRuleType.MODIFY_HEADER, false, true, "twc-privacy", false, "exempt", false, false, false, "twc-privacy", false, "usa", false, RewriteRuleReplaceType.ONLY_FIRST);
    			config.addRule(RewriteRuleType.MODIFY_HEADER, false, true, "twc-privacy", false, "[A-Za-z0-9\\.\\-]+", true, false, false, "twc-privacy", false, privacyRegime, false, RewriteRuleReplaceType.ONLY_FIRST);
    			config.addRule(RewriteRuleType.MODIFY_HEADER, false, true, "twc-geoip-country", false, "[A-Za-z0-9\\.\\-]+", true, false, false, "twc-geoip-country", false, country, false, RewriteRuleReplaceType.ONLY_FIRST);
    			//config.addRule(RewriteRuleType.MODIFY_HEADER, false, true, "twc-geoip-region", false, "GA", false, false, false, "twc-geoip-region", false, "GA", false, RewriteRuleReplaceType.ONLY_FIRST);
    			config.addRule(RewriteRuleType.MODIFY_HEADER, false, true, "twc-geoip-region", false, "[A-Za-z0-9\\.\\-]+", true, false, false, "twc-geoip-region", false, region, false, RewriteRuleReplaceType.ONLY_FIRST);
    			config.addLocation(Protocol.HTTPS, "dsx.weather.com", "", "/cms/*/privacy/en_US/twc-ios-flagship/*", "");

    			config.saveConfigurations(fileName);
	    		
	            //mapConfig = new CharlesConfiguration();
	    		 config.addMapping(CharlesConfiguration.Protocol.HTTPS, "dsx.weather.com", "", "/cms/v4/list/en_US/ios/video/app", "*",
	                    jsonPath, false);
	    		 config.saveConfigurations(fileName);
	            //proxy = new CharlesProxy("localhost", CONFIG_FILE_PATH);
	        } catch (CharlesProxyException e) {
	            Assert.fail(e.getMessage());
	        }

	        //proxy.startCharlesProxy();
	        //proxy.enableMapLocal();
	 }
	 
	 public void reWriteContentModeAndMapLocalForEditorialVideoHeadLineCardWhenNoTunnelBearAndroid(String fileName, String jsonPath, String contentMode, String privacyRegime, String country, String region) {
	        //String jsonPath = "src/test/resources/SevereInsightAtlanta.json";
	        try {
	        	/*final List<File> configFiles = new ArrayList<File>();
	    		final File parentDir = new File(Constants.PATH_USER_HOME);
	    		parentDir.mkdirs();
	    		final File configFile = new File(parentDir, fileName);
	    		configFile.setWritable(true);*/

	    		// Create Charles and configure rewrite to enable Breaking News
	    		 CharlesConfiguration config = new CharlesConfiguration();

	    		 config.addLocation(Protocol.HTTPS, "api.weather.com", "", "/v3/aggcommon/" + "*vt1contentMode*", "*");
	    		 config.addRule(RewriteRuleType.BODY, false, true, "", false, "\"mode\"\\s*:\\s*\"[A-Za-z0-9]*\"", true, false, false, "", false, "\"mode\" : \"" + contentMode + "\"", false, RewriteRuleReplaceType.ONLY_FIRST);
	    		 config.saveConfigurations(fileName);

	    		//return configFile;
	    		
	    		//config.addRule(RewriteRuleType.MODIFY_HEADER, false, true, "twc-privacy", false, "exempt", false, false, false, "twc-privacy", false, "usa", false, RewriteRuleReplaceType.ONLY_FIRST);
 			config.addRule(RewriteRuleType.MODIFY_HEADER, false, true, "twc-privacy", false, "[A-Za-z0-9\\.\\-]+", true, false, false, "twc-privacy", false, privacyRegime, false, RewriteRuleReplaceType.ONLY_FIRST);
 			config.addRule(RewriteRuleType.MODIFY_HEADER, false, true, "twc-geoip-country", false, "[A-Za-z0-9\\.\\-]+", true, false, false, "twc-geoip-country", false, country, false, RewriteRuleReplaceType.ONLY_FIRST);
 			//config.addRule(RewriteRuleType.MODIFY_HEADER, false, true, "twc-geoip-region", false, "GA", false, false, false, "twc-geoip-region", false, "GA", false, RewriteRuleReplaceType.ONLY_FIRST);
 			config.addRule(RewriteRuleType.MODIFY_HEADER, false, true, "twc-geoip-region", false, "[A-Za-z0-9\\.\\-]+", true, false, false, "twc-geoip-region", false, region, false, RewriteRuleReplaceType.ONLY_FIRST);
 			config.addLocation(Protocol.HTTPS, "dsx.weather.com", "", "/cms/*/privacy/en_US/*/*", "");

 			config.saveConfigurations(fileName);
	    		
	            //mapConfig = new CharlesConfiguration();
	    		 config.addMapping(CharlesConfiguration.Protocol.HTTPS, "dsx.weather.com", "", "/cms/v4/list/en_US/mobile/video/app", "*",
	                    jsonPath, false);
	    		 config.saveConfigurations(fileName);
	            //proxy = new CharlesProxy("localhost", CONFIG_FILE_PATH);
	        } catch (CharlesProxyException e) {
	            Assert.fail(e.getMessage());
	        }

	        //proxy.startCharlesProxy();
	        //proxy.enableMapLocal();
	 }
	
}

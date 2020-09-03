File propertiesFile = new File(new File(basedir, "target"), "app.properties");

Properties properties = new Properties();
properties.load(new FileInputStream(propertiesFile));

if (!properties.getProperty("released.ports", "").startsWith("[")) {
	throw new RuntimeException("Missing released.properties")
}


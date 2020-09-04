File properties1 = new File(new File(basedir, "target"), "app-1.properties");
File properties2 = new File(new File(basedir, "target"), "app-2.properties");

if(!properties1.exists()) {
	throw new RuntimeException("test-module-01 did not finish correctly")
}

if(!properties2.exists()) {
	throw new RuntimeException("test-module-02 did not finish correctly")
}

Properties properties = new Properties();
properties.load(new FileInputStream(properties1));

if (!properties.getProperty("released.ports", "").startsWith("[")) {
	throw new RuntimeException("Missing released.properties")
}


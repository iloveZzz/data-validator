package com.yss.rules.datavalidator

import com.yss.rules.datavalidator.engine.RulesEngineBuild
import org.codehaus.groovy.control.CompilerConfiguration

/**
 * @author daomingzhu* @date 2020/4/7 19:53
 */
class TestShell extends RulesEngineBuild{
    @Override
    Object run() {
        String script = readFileContent("src/main/resources/RuleFile");
        def config = new CompilerConfiguration()
        config.setScriptBaseClass(RulesEngineBuild.class.getName())
        new GroovyShell(this.class.classLoader,config).evaluate(script)
    }

    static String readFileContent(String fileName) {
        File file = new File(fileName);
        BufferedReader reader = null;
        StringBuffer sbf = new StringBuffer();
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempStr;
            while ((tempStr = reader.readLine()) != null) {
                sbf.append(tempStr);
            }
            reader.close();
            return sbf.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return sbf.toString();
    }


}

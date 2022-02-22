package com.wisecoders.dbschema.dynamodb;

import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.control.customizers.ImportCustomizer;

public class GroovyConfig extends CompilerConfiguration {

    public static final GroovyConfig CONFIG = new GroovyConfig();

    public GroovyConfig(){
        //setScriptBaseClass( D2StoreActions.class.getName() );
        addImports();
        setVerbose( true);
    }

    private void addImports(){
        final ImportCustomizer importCustomizer = new ImportCustomizer();
        //importCustomizer.addImports( D2Action.class.getName() );

        addCompilationCustomizers( importCustomizer );
    }
}


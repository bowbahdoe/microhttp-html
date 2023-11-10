module dev.mccue.microhttp.html {
    requires dev.mccue.reasonphrase;

    requires transitive org.microhttp;
    requires transitive dev.mccue.html;
    requires transitive dev.mccue.microhttp.handler;

    exports dev.mccue.microhttp.html;
}
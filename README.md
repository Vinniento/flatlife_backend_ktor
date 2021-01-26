# flatlife_backend_ktor

What is it?
REST Api for a simple flat management app.

Prerequisites:
--locally installed H2 DB software

application.conf file should look like:

---------START--------------

    ktor {
        deployment {
            sslPort = 8002
        }
        application {
            modules = [ wfp2.flatlife.ApplicationKt.module ]
        }

        security
        {
            ssl {
                    keyStore = /build/flatlife.jks
                    keyAlias = flatlife_keystore
                    keyStorePassword = nlkjnlkj
                    privateKeyPassword = nlkjnlkj
                }
        }
    }

-----------END----------------

For HTTPS traffic a .jks file is needed, which is generate by writing the following into a cmd and copying the outputed file into /build/:
 
 keytool -genkey -v -keystore flatlife.jks -alias flatlife_keystore -keyalg RSA -keysize 4096

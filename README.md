# drawforall
drawforall

How to create a different Publick Key
1. open the command line in the /bin directory of any JDK or JRE you have in handy:
cd $JAVA_HOME/bin
run the keytool command, with the corresponding parameters:
./keytool -genkeypair \
  -alias name-oauth-jwt \
  -keyalg RSA \
  -keypass name-pass \
  -keystore name-jwt.jks \
  -storepass name-pass

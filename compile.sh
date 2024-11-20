rm -rf ../Wordle
cp -r . ../Wordle
cd ../Wordle/src
javac -cp ".:../jna-5.15.0.jar" ./*.java
java Main
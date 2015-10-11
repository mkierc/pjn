cd PJN/lab10
echo %cd%
java -jar morfologik-tools-1.9.0-standalone.jar plstem -oe utf-8 -i rekcja-bez.txt -o output-bez.txt
java -jar morfologik-tools-1.9.0-standalone.jar plstem -oe utf-8 -i rekcja-do.txt -o output-do.txt
java -jar morfologik-tools-1.9.0-standalone.jar plstem -oe utf-8 -i rekcja-na.txt -o output-na.txt
java -jar morfologik-tools-1.9.0-standalone.jar plstem -oe utf-8 -i rekcja-od.txt -o output-od.txt
java -jar morfologik-tools-1.9.0-standalone.jar plstem -oe utf-8 -i rekcja-pod.txt -o output-pod.txt

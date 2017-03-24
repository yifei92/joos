all:
	chmod 777 buildscript && ./buildscript && chmod 777 joosc
clean:
	rm -rf build && rm joosc.jar && rm *.s
package:
	zip joosc.zip buildscript joosc Makefile -r src

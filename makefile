include makefile.base
JAR=circles.jar
OBJS=circles/circle.class circles/circle_list.class circles/circle_win.class

all: $(JAR)

$(JAR): $(OBJS)
	jar cfmv $(JAR) META-INF/MANIFEST.MF .

test: all
	java -jar $(JAR)

clean:
	rm -f $(JAR) circles/*.class


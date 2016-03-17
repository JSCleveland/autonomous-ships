-include user.mk

ifndef STARSECTOR_PATH
all:
	@echo ERROR: \$$STARSECTOR_PATH is undefined. Please set it in a local file named 'user.mk'.
	@false
else

VERSION=	0.3.0
DIR_NAME=	Autonomous Ships

JAVA_FILES=	$(shell find data src -name '*.java')
RESOURCE_DIRS=	data graphics src
RESOURCE_FILES=	$(foreach extension, csv json png, $(shell find $(RESOURCE_DIRS) -name '*.$(extension)'))
JAR_FILES=	starfarer.api.jar log4j-1.2.9.jar json.jar lwjgl_util.jar
#JAR_PATHS=	$(patsubst %,$(STARSECTOR_PATH)/Contents/Resources/Java/%,$(JAR_FILES))
JAR_PATHS=	$(foreach f, $(JAR_FILES), $(shell find '$(STARSECTOR_PATH)' -name $(f)))
JAR_CLASSPATH=	$(shell tr ' ' ':' <<< '$(JAR_PATHS)')
ZIP_FILE=	autonomous-ships-$(VERSION).zip

all: $(ZIP_FILE)

jars/org.tc.autonomous.jar: $(JAVA_FILES)
	@mkdir -p -v classes
	javac \
		-source 1.7 -target 1.7 \
		-d classes \
		-classpath '$(JAR_CLASSPATH)' \
		$(JAVA_FILES)
	@mkdir -p -v jars
	jar cvf $@ -C classes .

mod_info.json: mod_info.json.template
	@mkdir -p -v 'dist/$(DIR_NAME)'
	cat '$<' | sed 's/"version":".*",/"version":"$(VERSION)",/' > '$@'

$(ZIP_FILE): jars/org.tc.autonomous.jar $(RESOURCE_FILES) mod_info.json $(JAVA_FILES)
	@mkdir -p -v 'dist/$(DIR_NAME)'
	@echo $+ | xargs -n 1 dirname | sort -u | while read f ; do mkdir -p -v "dist/$(DIR_NAME)/$${f}" ; done
	@for f in $+ ; do cp -v "$${f}" "dist/$(DIR_NAME)/$${f}" ; done
	@rm -f -v '$@'
	cd 'dist' && zip -r -9 '../$@' '$(DIR_NAME)'

deploy: $(ZIP_FILE)
	unzip -o -d '$(STARSECTOR_PATH)/mods/' '$<'

upload: $(ZIP_FILE)
	s3cmd put '$<' s3://download.trancecode.org/starsector/autonomous-ships/ --acl-public

clean:
	@rm -rfv classes jars dist $(ZIP_FILE)

endif

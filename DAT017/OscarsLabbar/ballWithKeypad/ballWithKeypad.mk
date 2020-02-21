##
## Auto Generated makefile by CodeLite IDE
## any manual changes will be erased      
##
## Debug
ProjectName            :=ballWithKeypad
ConfigurationName      :=Debug
WorkspacePath          :=C:/Users/osann/Documents/GitProjects/SchoolProjects/DAT017/OscarsLabbar
ProjectPath            :=C:/Users/osann/Documents/GitProjects/SchoolProjects/DAT017/OscarsLabbar/ballWithKeypad
IntermediateDirectory  :=./Debug
OutDir                 := $(IntermediateDirectory)
CurrentFileName        :=
CurrentFilePath        :=
CurrentFileFullPath    :=
User                   :=osann
Date                   :=21/02/2020
CodeLitePath           :=C:/cseapp/CodeLite
LinkerName             :=$(CodeLiteDir)/tools/gcc-arm/bin/arm-none-eabi-g++.exe
SharedObjectLinkerName :=$(CodeLiteDir)/tools/gcc-arm/arm-none-eabi-g++.exe -shared -fPIC
ObjectSuffix           :=.o
DependSuffix           :=.o.d
PreprocessSuffix       :=.i
DebugSwitch            :=-g 
IncludeSwitch          :=-I
LibrarySwitch          :=-l
OutputSwitch           :=-o 
LibraryPathSwitch      :=-L
PreprocessorSwitch     :=-D
SourceSwitch           :=-c 
OutputFile             :=$(IntermediateDirectory)/$(ProjectName).elf
Preprocessors          :=
ObjectSwitch           :=-o 
ArchiveOutputSwitch    := 
PreprocessOnlySwitch   :=-E
ObjectsFileList        :="ballWithKeypad.txt"
PCHCompileFlags        :=
MakeDirCommand         :=makedir
RcCmpOptions           := 
RcCompilerName         :=
LinkOptions            :=  -T$(ProjectPath)/md407-ram.x -L$(ARM_V6LIB) -L$(ARM_GCC_V6LIB) -nostartfiles
IncludePath            :=  $(IncludeSwitch). $(IncludeSwitch). 
IncludePCH             := 
RcIncludePath          := 
Libs                   := $(LibrarySwitch)gcc $(LibrarySwitch)c_nano 
ArLibs                 :=  "gcc" "c_nano" 
LibPath                := $(LibraryPathSwitch). 

##
## Common variables
## AR, CXX, CC, AS, CXXFLAGS and CFLAGS can be overriden using an environment variables
##
AR       := $(CodeLiteDir)/tools/gcc-arm/bin/arm-none-eabi-ar.exe rcu
CXX      := $(CodeLiteDir)/tools/gcc-arm/bin/arm-none-eabi-g++.exe
CC       := $(CodeLiteDir)/tools/gcc-arm/bin/arm-none-eabi-gcc.exe
CXXFLAGS :=  -g -O0 -W $(Preprocessors)
CFLAGS   :=  -g -O0 -w -mthumb -march=armv6-m  -mfloat-abi=soft -std=c99 $(Preprocessors)
ASFLAGS  := 
AS       := $(CodeLiteDir)/tools/gcc-arm/bin/arm-none-eabi-as.exe


##
## User defined environment variables
##
CodeLiteDir:=C:\cseapp\CodeLite
ARM_V6LIB:=$(CodeLiteDir)/tools/gcc-arm/arm-none-eabi/lib/thumb/v6-m/nofp
ARM_GCC_V6LIB:=$(CodeLiteDir)/tools/gcc-arm/lib/gcc/arm-none-eabi/9.2.1/thumb/v6-m/nofp
ARM_M4SFPLIB:=$(CodeLiteDir)/tools/gcc-arm/arm-none-eabi/lib/thumb/v7e-m+fp/softfp
ARM_GCC_M4SFPLIB:=$(CodeLiteDir)/tools/gcc-arm/lib/gcc/arm-none-eabi/9.2.1/thumb/v7e-m+fp/softfp
ARM_M4HFPLIB:=$(CodeLiteDir)/tools/gcc-arm/arm-none-eabi/lib/thumb/v7e-m+fp/hard
ARM_GCC_M4HFPLIB:=$(CodeLiteDir)/tools/gcc-arm/lib/gcc/arm-none-eabi/9.2.1/thumb/v7e-m+fp/hard
Objects0=$(IntermediateDirectory)/ballWithKeypad.c$(ObjectSuffix) 



Objects=$(Objects0) 

##
## Main Build Targets 
##
.PHONY: all clean PreBuild PrePreBuild PostBuild MakeIntermediateDirs
all: $(OutputFile)

$(OutputFile): $(IntermediateDirectory)/.d $(Objects) 
	@$(MakeDirCommand) $(@D)
	@echo "" > $(IntermediateDirectory)/.d
	@echo $(Objects0)  > $(ObjectsFileList)
	$(LinkerName) $(OutputSwitch)$(OutputFile) @$(ObjectsFileList) $(LibPath) $(Libs) $(LinkOptions)

PostBuild:
	@echo Executing Post Build commands ...
	C:\cseapp\CodeLite/tools/gcc-arm/bin/arm-none-eabi-objcopy -S -O srec  ./Debug/ballWithKeypad.elf ./Debug/ballWithKeypad.s19
	C:\cseapp\CodeLite/tools/gcc-arm/bin/arm-none-eabi-objdump -D -S ./Debug/ballWithKeypad.elf > ./Debug/ballWithKeypad.dass
	@echo Done

MakeIntermediateDirs:
	@$(MakeDirCommand) "./Debug"


$(IntermediateDirectory)/.d:
	@$(MakeDirCommand) "./Debug"

PreBuild:


##
## Objects
##
$(IntermediateDirectory)/ballWithKeypad.c$(ObjectSuffix): ballWithKeypad.c $(IntermediateDirectory)/ballWithKeypad.c$(DependSuffix)
	$(CC) $(SourceSwitch) "C:/Users/osann/Documents/GitProjects/SchoolProjects/DAT017/OscarsLabbar/ballWithKeypad/ballWithKeypad.c" $(CFLAGS) $(ObjectSwitch)$(IntermediateDirectory)/ballWithKeypad.c$(ObjectSuffix) $(IncludePath)
$(IntermediateDirectory)/ballWithKeypad.c$(DependSuffix): ballWithKeypad.c
	@$(CC) $(CFLAGS) $(IncludePath) -MG -MP -MT$(IntermediateDirectory)/ballWithKeypad.c$(ObjectSuffix) -MF$(IntermediateDirectory)/ballWithKeypad.c$(DependSuffix) -MM ballWithKeypad.c

$(IntermediateDirectory)/ballWithKeypad.c$(PreprocessSuffix): ballWithKeypad.c
	$(CC) $(CFLAGS) $(IncludePath) $(PreprocessOnlySwitch) $(OutputSwitch) $(IntermediateDirectory)/ballWithKeypad.c$(PreprocessSuffix) ballWithKeypad.c


-include $(IntermediateDirectory)/*$(DependSuffix)
##
## Clean
##
clean:
	$(RM) -r ./Debug/



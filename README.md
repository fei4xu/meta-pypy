# meta-pypy
 PyPy(https://bitbucket.org/pypy/pypy) support for Bitbake/Yocto/OpenEmbedded/OE
 
 * Cross-translation of pypy-core package is done out-of-tree. Patches welcome!
 * Supports cross-compiling cffi modules using native bitbake tool flow
 * Supports pypy's version of numpy (https://bitbucket.org/pypy/numpy/)
 
# cross translating pypy on Ubuntu 14.04 x64
* Follow general procedure from http://rpython.readthedocs.org/en/latest/arm.html#arm
* sudo apt-get install zlib1g:i386 libbz2-1.0:i386 libssl1.0.0:i386 libexpat1:i386 libffi6:i386 libtinfo5:i386  
* IMAGE_INSTALL += "bash libffi libssl libffi-dev expat bzip2 ncurses-libncurses 
 
# Initialize rootfs for scratchbox2

rm -rf /srv/chroot/precise_arm*  
runqemu-extract-sdk tmp/deploy/images/\*/core-image-minimal-\*.tar.bz2 /srv/chroot/precise_arm  
cp /usr/bin/qemu-arm-static /srv/chroot/precise_arm/usr/bin/qemu-arm-static  

#Initialize scratchbox2 with Bitbake's $CC & $CFLAGS
sb2-init -C "-march=armv5e -marm -mthumb-interwork --sysroot=/home/mzakharo/pypy/poky/build/tmp/sysroots/qemuarm -I/usr/lib/libffi-3.2.1/include" -c `which qemu-arm` ARM  /home/mzakharo/pypy/poky/build/tmp/sysroots/x86_64-linux/usr/bin/arm-poky-linux-gnueabi/arm-poky-linux-gnueabi-gcc



#Add to local.conf for 64-bit host
NO32LIBS=0

# meta-pypy
 PyPy(https://bitbucket.org/pypy/pypy) support for Bitbake/Yocto/OpenEmbedded/OE
 
 * Cross-translation of pypy-core package is done out-of-tree. Patches welcome!
 * Supports cross-compiling cffi modules using native bitbake tool flow
 * Supports pypy's version of numpy (https://bitbucket.org/pypy/numpy/)
 
# cross translating pypy on Ubuntu 14.04 x64
* Follow general procedure from http://rpython.readthedocs.org/en/latest/arm.html#arm
* `sudo apt-get install zlib1g:i386 libbz2-1.0:i386 libssl1.0.0:i386 libexpat1:i386 libffi6:i386 libtinfo5:i386`
* `sudo mkdir -p /srv/chroot/precise_arm`
* `sudo chown -R $USER /srv/chroot`
* `sudo apt-get install  qemu-user-static schroot scratchbox2`
* Modify `/etc/schroot/schroot.conf` as per   http://rpython.readthedocs.org/en/latest/arm.html#creating-a-qemu-based-arm-chroot

# Initialize rootfs
`add NO32LIBS=0 to conf/local.conf` #for 64 bit hosts  
`bitbake pypy-rootfs` #to build rootfs for cross-translation  
`export MACHINE=qemuarm`  #replace with your MACHINE from conf/local.conf  
`rm -rf /srv/chroot/precise_arm*`  
`runqemu-extract-sdk tmp/deploy/images/$MACHINE/pypy-rootfs-$MACHINE.tar.gz /srv/chroot/precise_arm`  
`cp /usr/bin/qemu-arm-static /srv/chroot/precise_arm/usr/bin/qemu-arm-static`  

#Initialize scratchbox2

To obtain the desired variables, just open a dev console `bitbake -c devshell bash` and ``echo  $CC $CFLAGS `which $CC```     
* Below is an example for qemuarm on Yocto 2.0:
* `cd /srv/chroot/precise_arm`  
* ``sb2-init -C "-march=armv5e -marm -mthumb-interwork --sysroot=/home/mzakharo/pypy/poky/build/tmp/sysroots/qemuarm -I/usr/lib/libffi-3.2.1/include" -c `which qemu-arm` ARM /home/mzakharo/pypy/poky/build/tmp/sysroots/x86_64-linux/usr/bin/arm-poky-linux-gnueabi/arm-poky-linux-gnueabi-gcc``

#Translation
 `wget https://bitbucket.org/pypy/pypy/downloads/pypy-4.0.1-linux.tar.bz2`  
 `tar -jxvf pypy-4.0.1-linux.tar.bz2`  
 `wget https://bitbucket.org/pypy/pypy/downloads/pypy-4.0.1-src.zip`  
 `unzip pypy-4.0.1-src.zip && cd pypy-4.0.1-src`
 `patch -p1  < $META-PYPY_SRC/patches/fix_unix_compiler.patch` # [issue 2217]( https://bitbucket.org/pypy/pypy/issues/2217/cross-translating-cffi-modules-unable-to)  
 `patch -p1 < $META-PYPY_SRC/patches/fix_64bit_host.patch` # [issue 2218]( https://bitbucket.org/pypy/pypy/issues/2218/cross-translating-on-64-bit-host-for-arm)  
 * Continue with steps from http://rpython.readthedocs.org/en/latest/arm.html#translation  
 `export SB2=/srv/chroot/precise_arm`  
 `export SB2OPT='-t ARM'`  
 `cd pypy/goal`  
 `../../../pypy-4.0.1-linux/bin/pypy ../../rpython/bin/rpython -Ojit --platform=arm --gcrootfinder=shadowstack --jit-backend=arm targetpypystandalone.py`  

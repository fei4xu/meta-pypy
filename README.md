# meta-pypy
 PyPy(https://bitbucket.org/pypy/pypy) support for Bitbake/Yocto/OpenEmbedded
 
 * Supports python setup.py build/install  
 * Supports cross-compiling python C extensions
 * Supports pypy's fork of numpy (https://bitbucket.org/pypy/numpy/)
 * PyPy cross-translation flow relies heavily on scratchbox2, hence the pypy-core package is built outside of the bitbake flow
 
# Cross translating PyPy
* Following the general procedure from http://rpython.readthedocs.org/en/latest/arm.html#arm
* Tested with Yocto 1.8.1/2.0, qemuarm, beaglebone targets on Ubuntu 14.04 x64 host
* `sudo apt-get install zlib1g:i386 libbz2-1.0:i386 libssl1.0.0:i386 libexpat1:i386 libffi6:i386 libtinfo5:i386`
* `sudo mkdir -p /srv/chroot/precise_arm`
* `sudo chown -R $USER /srv/chroot`
* `sudo apt-get install  qemu-user-static schroot scratchbox2`
* Modify `/etc/schroot/schroot.conf` as per   http://rpython.readthedocs.org/en/latest/arm.html#creating-a-qemu-based-arm-chroot

# Initialize rootfs
`add NO32LIBS=0` to `conf/local.conf` #for 64 bit hosts  
`bitbake pypy-rootfs` #to build rootfs for cross-translation  
`export MACHINE=beaglebone`  #replace with your MACHINE from conf/local.conf  
`rm -rf /srv/chroot/precise_arm*`  
`runqemu-extract-sdk tmp/deploy/images/$MACHINE/pypy-rootfs-$MACHINE.tar.gz /srv/chroot/precise_arm`  
`cp /usr/bin/qemu-arm-static /srv/chroot/precise_arm/usr/bin/qemu-arm-static`  

#Initialize scratchbox2

sb2-init needs to be configured wiht bitbake's compiler. To obtain the desired variables, just open a dev console `bitbake -c devshell bash` and ``echo  $CC  `which $CC``` . Also add -I/usr/lib/libffi-3.2.1/include to -C.   
* Below is an example for beaglebone on Yocto 1.8:
* `cd /srv/chroot/precise_arm`  
* ``sb2-init -C "-march=armv7-a -mfloat-abi=hard -mfpu=neon -mtune=cortex-a8 --sysroot=/home/mzakharo/dev/pypy/poky/build/tmp/sysroots/beaglebone -I/usr/lib/libffi-3.2.1/include" -c `which qemu-arm` ARM /home/mzakharo/dev/pypy/poky/build/tmp/sysroots/x86_64-linux/usr/bin/arm-poky-linux-gnueabi/arm-poky-linux-gnueabi-gcc``

#Translation
 `wget https://bitbucket.org/pypy/pypy/downloads/pypy-4.0.1-linux.tar.bz2`  
 `tar -jxvf pypy-4.0.1-linux.tar.bz2`  
 `wget https://bitbucket.org/pypy/pypy/downloads/pypy-4.0.1-src.zip`  
 `unzip pypy-4.0.1-src.zip && cd pypy-4.0.1-src`  
 `patch -p1  < $meta-pypy/patches/fix_unix_compiler.patch` # [issue 2217]( https://bitbucket.org/pypy/pypy/issues/2217/cross-translating-cffi-modules-unable-to)  
 `patch -p1 < $META-PYPY_SRC/patches/fix_64bit_host.patch` # [issue 2218]( https://bitbucket.org/pypy/pypy/issues/2218/cross-translating-on-64-bit-host-for-arm)  
 * Continue with steps from http://rpython.readthedocs.org/en/latest/arm.html#translation  
 `export SB2=/srv/chroot/precise_arm`  
 `export SB2OPT='-t ARM'`  
 `cd pypy/goal`  
 `../../../pypy-4.0.1-linux/bin/pypy ../../rpython/bin/rpython -Ojit --platform=arm --gcrootfinder=shadowstack --jit-backend=arm targetpypystandalone.py`

#Package
 Example creating package for beaglebone  
 `cd ../tool/release`  
 `sb2 -t ARM ../../goal/pypy-c package.py --nostrip --without-tk --without-sqlite3 --archive-name pypy-4.0.1-cortexa8 --targetdir $meta-pypy/recipes-devtools/pypy/pypy/`  
 
#Profit
The following commands become available:  
`bitbake pypy`  
`bitbake pypy-numpy`  

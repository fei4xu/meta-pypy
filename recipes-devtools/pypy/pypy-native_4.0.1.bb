SUMMARY = "PyPy Interpreter"
SECTION = "base"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "filce://${S}/LICENSE;md5=e915d0baff973dba7610ea88289dcddd"

inherit native

#RDEPENDS_${PN} = "libffi libssl expat bzip2 zlib ncurses-libncurses"

SRC_URI = "https://bitbucket.org/pypy/pypy/downloads/pypy-${PV}-linux.tar.bz2 \
     file://sysconfig_fix.patch\
     "
S="${WORKDIR}/pypy-${PV}-linux"

SRC_URI[md5sum] = "d1d03aa44df354a3f589473a51406795"
SRC_URI[sha256sum] = "721920fcbb6aefc9a98e868e32b7f4ea5fd68b7f9305d08d0a2595327c9c0611"

do_compile () {
}
do_clean () {
}
do_configure() {
}

do_install () {

	install -d ${D}${bindir}
	install ${S}/bin/pypy  ${D}${bindir}
    oe_libinstall  -C ${S}/bin -so libpypy-c ${D}${bindir}

	install -d ${D}${includedir}
    cp -dr --no-preserv=ownership ${S}/include/* ${D}${includedir}

    cp -dr --no-preserv=ownership ${S}/lib-python/ ${D}${prefix}
    cp -dr --no-preserv=ownership ${S}/lib_pypy/ ${D}${prefix}
    cp -dr --no-preserv=ownership ${S}/site-packages/ ${D}${prefix}

    # Add a symlink to the native Python so that scripts can just invoke
	# "nativepython" and get the right one without needing absolute paths
	# (these often end up too long for the #! parser in the kernel as the
	# buffer is 128 bytes long).
	ln -s ${bindir}/pypy ${D}${bindir}/nativepypy
	install -d ${D}${bindir}/pypy-native
	ln -s ${bindir}/pypy ${D}${bindir}/pypy-native/pypy



	# We don't want modules in ~/.local being used in preference to those
	# installed in the native sysroot, so disable user site support.
	sed -i -e 's,^\(ENABLE_USER_SITE = \).*,\1False,' ${D}${prefix}/lib-python/2.7/site.py


}

sysroot_stage_dirs_append () {
	sysroot_stage_dir $from${prefix} $to${STAGING_DIR_HOST}${prefix}
}


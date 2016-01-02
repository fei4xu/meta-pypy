SUMMARY = "PyPy Interpreter"
SECTION = "base"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "filce://${S}/LICENSE;md5=e915d0baff973dba7610ea88289dcddd"

inherit native

#RDEPENDS_${PN} = "libffi libssl expat bzip2 zlib ncurses-libncurses"

SRC_URI = "file://pypy-${PV}-linux.tar.bz2"
S="${WORKDIR}/pypy-${PV}-linux"


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

	# We don't want modules in ~/.local being used in preference to those
	# installed in the native sysroot, so disable user site support.
	sed -i -e 's,^\(ENABLE_USER_SITE = \).*,\1False,' ${D}${prefix}/lib-python/2.7/site.py


}

sysroot_stage_dirs_append () {
	sysroot_stage_dir $from${prefix} $to${STAGING_DIR_HOST}${prefix}
}


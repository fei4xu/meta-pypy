SUMMARY = "PyPy Interpreter"
SECTION = "base"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "filce://${S}/LICENSE;md5=e915d0baff973dba7610ea88289dcddd"

RDEPENDS_${PN} = "libffi libssl expat bzip2 zlib ncurses-libncurses"

ARCH="${ARMPKGARCH}"

SRC_URI = "file://pypy-${PV}-${ARCH}.tar.bz2"
S="${WORKDIR}/pypy-${PV}-${ARCH}"


do_compile () {
}
do_clean () {
}
do_configure() {
}

do_install () {

	install -d ${D}${bindir}
	install ${S}/bin/pypy  ${D}${bindir}

    oe_libinstall  -C ${S}/bin -so libpypy-c ${D}${libdir}

	install -d ${D}${includedir}
    cp -dr --no-preserv=ownership ${S}/include/ ${D}${includedir}

    cp -dr --no-preserv=ownership ${S}/lib-python/ ${D}${prefix}
    cp -dr --no-preserv=ownership ${S}/lib_pypy/ ${D}${prefix}
    cp -dr --no-preserv=ownership ${S}/site-packages/ ${D}${prefix}
}

FILES_${PN} +=  "\
    ${libdir}/* \
    ${includedir}/* \
    ${prefix}/lib-python/* \
    ${prefix}/lib_pypy/* \
    ${prefix}/site-packages/* \
    "
FILES_${PN}-dbg += "${prefix}/lib_pypy/.debug/*"

PACKAGES= "${PN} ${PN}-dbg"


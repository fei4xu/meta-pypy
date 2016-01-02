SUMMARY = "Downloads, builds, installs, upgrades, and uninstalls Python packages"
HOMEPAGE = "https://pypi.python.org/pypi/setuptools"
SECTION = "devel/python"
LICENSE = "Python-2.0 | ZPL-2.0"
LIC_FILES_CHKSUM = "file://setup.py;beginline=78;endline=78;md5=8a314270dd7a8dbca741775415f1716e"

SRCNAME = "setuptools"

PROVIDES = "pypy-distribute"

DEPENDS += "pypy"
DEPENDS_class-native += "pypy-native"

inherit distutils_pypy

SRC_URI = "https://pypi.python.org/packages/source/s/setuptools/setuptools-${PV}.tar.gz"
SRC_URI[md5sum] = "78353b1f80375ca5e088f4b4627ffe03"
SRC_URI[sha256sum] = "f90ed8eb70b14b0594ba74e9de4ffca040c0ec8ee505cbf3570499467859f71a"

S = "${WORKDIR}/${SRCNAME}-${PV}"


DISTUTILS_INSTALL_ARGS += "--install-lib=${D}${libdir}/${PYTHON_DIR}/site-packages"

do_install_append() {
    mv ${D}${bindir}/easy_install ${D}${bindir}/easypypy_install
    rm ${D}${bindir}/easy_install-2.7
}


do_configure() {
}

RREPLACES_${PN} = "pypy-distribute"
RPROVIDES_${PN} = "pypy-distribute"
RCONFLICTS_${PN} = "pypy-distribute"

BBCLASSEXTEND = "native nativesdk"

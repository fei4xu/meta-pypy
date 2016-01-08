SUMMARY = "NumPy is the fundamental package for scientific computing with Python"
HOMEPAGE = "https://bitbucket.org/pypy/numpy/"
SECTION = "devel/python"

#FIXME: use proper PyPy-Numpy license here
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=f87832d854acbade6e9f5c601c8b30b1"

SRC_URI = "https://bitbucket.org/pypy/numpy/get/pypy-${PV}.tar.bz2"
SRC_URI[md5sum] = "dce4cc7be250eb55e3972da28c85c3e7"
SRC_URI[sha256sum] = "67ccce67b898fba856b568e679aa6352bba6da9d14f743b8ebdd292218c4871e"
#tag=4.0.1
S = "${WORKDIR}/pypy-numpy-b45bd6a57e37"
inherit setuptools-pypy

export DISTUTILS_DEBUG="1"

FILES_${PN}-staticdev += "${PYTHON_SITEPACKAGES_DIR}/numpy/core/lib/*.a"

#avoid clashing with python-numpy
do_install_append() {
    mv ${D}${bindir}/f2py ${D}${bindir}/f2pypy
}

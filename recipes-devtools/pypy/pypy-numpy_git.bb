SUMMARY = "NumPy is the fundamental package for scientific computing with Python"
HOMEPAGE = "https://bitbucket.org/pypy/numpy/"
SECTION = "devel/python"

#FIXME: use proper PyPy-Numpy license here
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=f87832d854acbade6e9f5c601c8b30b1"

PV = "git${SRCPV}"

SRC_URI = "git://bitbucket.org/pypy/numpy.git;protocol=https"
#tag=4.0.1
SRCREV="b45bd6a57e374d1e7401fe095daf5b45dc739f51"

S = "${WORKDIR}/git"
inherit setuptools-pypy

export DISTUTILS_DEBUG="1"

#needed for some targets (ex: Beaglebone, but not qemuarm)
export CFLAGS:="${CFLAGS} -fPIC"

FILES_${PN}-staticdev += "${PYTHON_SITEPACKAGES_DIR}/numpy/core/lib/*.a"

#avoid clashing with python-numpy
do_install_append() {
    mv ${D}${bindir}/f2py ${D}${bindir}/f2pypy
}

DESCRIPTION = "An implementation of JSON Schema validation for Python"
HOMEPAGE = "https://pypi.python.org/pypi/vcversioner"
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://PKG-INFO;beginline=8;endline=8;md5=8227180126797a0148f94f483f3e1489"

SRCNAME = "txdbus"

#FIXME: remove pypy-twisted-src when distutils-pypy fixes .pyc compilation bug
RDEPENDS_${PN} +="pypy-twisted-core pypy-twisted-src"

SRC_URI = "http://pypi.python.org/packages/source/t/${SRCNAME}/${SRCNAME}-${PV}.tar.gz"


SRC_URI[md5sum] = "9764d696a72d1fc999edc353d693c8ad"
SRC_URI[sha256sum] = "c34d09b12d34db9fdcf2d71e168c1a46314a5dda027f932a085bb338da934222"

S = "${WORKDIR}/${SRCNAME}-${PV}"

inherit setuptools-pypy

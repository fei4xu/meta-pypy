def pypi_package(d):
    bpn = d.getVar('BPN', True)
    if bpn.startswith('pypy-'):
        return bpn[5:]
    return bpn

PYPI_PACKAGE ?= "${@pypi_package(d)}"

def pypi_src_uri(d):
    package = d.getVar('PYPI_PACKAGE', True)
    pv = d.getVar('PV', True)
    return 'https://pypi.python.org/packages/source/%s/%s/%s-%s.tar.gz' % (package[0], package, package, pv)

PYPI_SRC_URI ?= "${@pypi_src_uri(d)}"

HOMEPAGE ?= "https://pypi.python.org/pypi/${PYPI_PACKAGE}/"
SECTION = "devel/pypy"
SRC_URI += "${PYPI_SRC_URI}"
S = "${WORKDIR}/${PYPI_PACKAGE}-${PV}"

inherit setuptools-pypy

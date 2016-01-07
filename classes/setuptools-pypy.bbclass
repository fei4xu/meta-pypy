inherit distutils_pypy


#needed for some targets (ex: Beaglebone, but not qemuarm)
CFLAGS += "-fPIC"

DEPENDS += "pypy-distribute-native"

DISTUTILS_INSTALL_ARGS = "--root=${D} \
    --prefix=${prefix} \
    --install-lib=${PYTHON_SITEPACKAGES_DIR} \
    --install-data=${datadir}"

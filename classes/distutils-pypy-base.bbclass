DEPENDS  += "${@["${PYTHON_PN}-native ${PYTHON_PN}", ""][(d.getVar('PACKAGES', True) == '')]}"
#RDEPENDS_${PN} += "${@['', '${PYTHON_PN}-core']['${CLASSOVERRIDE}' == 'class-target']}"
RDEPENDS_${PN} += "${@['', '${PYTHON_PN}']['${CLASSOVERRIDE}' == 'class-target']}"

inherit distutils-pypy-common-base pypynative

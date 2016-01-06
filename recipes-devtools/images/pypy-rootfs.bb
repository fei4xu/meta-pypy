SUMMARY = "A small rootfs allow pypy cross-translation"
LICENSE = "MIT"
IMAGE_INSTALL = "packagegroup-core-boot ${ROOTFS_PKGMANAGE_BOOTSTRAP} ${CORE_IMAGE_EXTRA_INSTALL}"
IMAGE_INSTALL += "bash libffi libssl libffi-dev expat bzip2 ncurses-libncurses"
IMAGE_FSTYPES = "tar.gz"
inherit core-image

#helper scripts
export _CFLAGS_:="${CFLAGS}"
export _CC_:="${CC}"

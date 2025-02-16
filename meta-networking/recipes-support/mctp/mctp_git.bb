SUMMARY = "Management Component Control Protocol utilities"
HOMEPAGE = "http://www.github.com/CodeConstruct/mctp"
SECTION = "net"
LICENSE = "GPLv2"

LIC_FILES_CHKSUM = "file://LICENSE;md5=4cc91856b08b094b4f406a29dc61db21"

PV = "0.1+git${SRCPV}"

SRCREV = "072bafe725c50329f99cf9d3b2624e8799e8163a"

SRC_URI = "git://github.com/CodeConstruct/mctp;branch=main;protocol=https \
           file://0001-build-Adjust-for-kernel-mctp.h.patch \
          "

S = "${WORKDIR}/git"

inherit meson pkgconfig systemd

PACKAGECONFIG ??= " \
    ${@bb.utils.filter('DISTRO_FEATURES', 'systemd', d)} \
"

# mctpd will only be built if pkg-config detects libsystemd; in which case
# we'll want to declare the dep and install the service.
PACKAGECONFIG[systemd] = ",,systemd,libsystemd"
SYSTEMD_SERVICE:${PN} = "mctpd.service"

do_install:append () {
    if ${@bb.utils.contains('PACKAGECONFIG', 'systemd', 'true', 'false', d)}; then
        install -d ${D}${systemd_system_unitdir}
        install -m 0644 ${S}/conf/mctpd.service \
                ${D}${systemd_system_unitdir}/mctpd.service
    fi
}

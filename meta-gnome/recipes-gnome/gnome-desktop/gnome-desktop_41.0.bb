SUMMARY = "GNOME library for reading .desktop files"
SECTION = "x11/gnome"
LICENSE = "GPLv2 & LGPLv2"
LIC_FILES_CHKSUM = " \
    file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263 \
    file://COPYING.LIB;md5=5f30f0716dfdd0d91eb439ebec522ec2 \
"

GNOMEBASEBUILDCLASS = "meson"

inherit gnomebase itstool pkgconfig upstream-version-is-even gobject-introspection features_check gtk-doc

REQUIRED_DISTRO_FEATURES = "x11"
# gobject-introspection is mandatory and cannot be configured
REQUIRED_DISTRO_FEATURES += "gobject-introspection-data"

GIR_MESON_OPTION = ""

SRC_URI[archive.sha256sum] = "69cb1d3d9a10700eb66348ef1c0e66a855fc5a97ae62902df97a499da11562d2"
SRC_URI += " \
    file://gnome-desktop-thumbnail-don-t-assume-time_t-is-long.patch \
    file://0001-meson.build-Disable-libseccomp-for-all-archs.patch \
"

DEPENDS += "gsettings-desktop-schemas virtual/libx11 gtk+3 startup-notification xkeyboard-config iso-codes udev"

GTKDOC_MESON_OPTION = "gtk_doc"
EXTRA_OEMESON = "-Ddesktop_docs=false"

PACKAGES =+ "libgnome-desktop"
RDEPENDS:${PN} += "libgnome-desktop"
FILES:libgnome-desktop = " \
    ${libdir}/lib*${SOLIBS} \
    ${datadir}/libgnome-desktop*/pnp.ids \
    ${datadir}/gnome/*xml \
"

RRECOMMENDS:libgnome-desktop += "gsettings-desktop-schemas"

PROVIDES += "gnome-desktop3"
RPROVIDES:${PN} += "gnome-desktop3"
RPROVIDES:libgnome-desktop += "gnome-desktop3"

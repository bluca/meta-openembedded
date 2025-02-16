# SPDX-FileCopyrightText: Huawei Inc.
#
# SPDX-License-Identifier: MIT

HOMEPAGE = "https://lvgl.io/"
DESCRIPTION = "LVGL is an OSS graphics library to create embedded GUI"
SUMMARY = "Light and Versatile Graphics Library"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENCE.txt;md5=bf1198c89ae87f043108cea62460b03a"

# TODO: Pin upstream release (current is v8.0.3-dev-239-g7b7bed37d)
SRC_URI = "gitsm://github.com/lvgl/lvgl;destsuffix=${S};protocol=https;nobranch=1"
SRCREV = "7b7bed37d3e937c59ec99fccba58774fbf9f1930"

REQUIRED_DISTRO_FEATURES = "wayland"

inherit cmake
inherit features_check

S = "${WORKDIR}/${PN}-${PV}"

EXTRA_OECMAKE += "-Dinstall:BOOL=ON"

LVGL_CONFIG_LV_MEM_CUSTOM ?= "0"

# Upstream does not support a default configuration
# but propose a default "disabled" template, which is used as reference
# More configuration can be done using external configuration variables
do_configure:prepend() {
    [ -r "${S}/lv_conf.h" ] \
        || sed -e 's|#if 0 .*Set it to "1" to enable .*|#if 1 // Enabled|g' \
	    -e "s|\(#define LV_MEM_CUSTOM .*\)0|\1${LVGL_CONFIG_LV_MEM_CUSTOM}|g" \
            < "${S}/lv_conf_template.h" > "${S}/lv_conf.h"
}

FILES:${PN}-dev += "\
    ${includedir}/${PN}/ \
    ${includedir}/${PN}/lvgl/ \
    "

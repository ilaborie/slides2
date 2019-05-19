extern crate jni;
extern crate qrcode;
// extern crate image;

use qrcode::QrCode;
// use image::Luma;

use jni::JNIEnv;
use jni::objects::{JClass, JString};
use jni::sys::jstring;

#[no_mangle]
#[allow(non_snake_case)]
pub extern "system" fn Java_io_github_ilaborie_slides2_kt_jvm_tools_QrCode_qrCode(
    env: JNIEnv,
    _class: JClass,
    input: JString,
) -> jstring {
    let input: String = env
        .get_string(input)
        .expect("Couldn't get java string!")
        .into();

    let code = QrCode::new(input).unwrap();

    let result = code.render()
        .light_color("<span class=\"light\"></span>")
        .dark_color("<span class=\"dark\"></span>")
        .build();

    env
        .new_string(result)
        .expect("Couldn't create java string!")
        .into_inner()
}
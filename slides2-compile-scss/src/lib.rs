extern crate jni;
extern crate sass_rs;

use jni::JNIEnv;
use jni::objects::{JClass, JString};
use jni::sys::jstring;
use sass_rs::{compile_file, Options};
use sass_rs::OutputStyle;

#[no_mangle]
#[allow(non_snake_case)]
pub extern "system" fn Java_io_github_ilaborie_slides2_kt_jvm_tools_ScssToCss_scssFileToCss(
    env: JNIEnv,
    _class: JClass,
    input: JString,
) -> jstring {
    let input: String = env
        .get_string(input)
        .expect("Couldn't get java string!")
        .into();

    let mut options = Options::default();
    options.output_style = OutputStyle::Compressed;

    let compiled = compile_file(&input, options)
        .expect("Could not compile SCSS");

    env
        .new_string(compiled)
        .expect("Couldn't create java string!")
        .into_inner()
}

#[cfg(test)]
mod tests {
    #[test]
    fn it_works() {}
}

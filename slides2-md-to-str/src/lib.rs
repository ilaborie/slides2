extern crate jni;

use jni::JNIEnv;
use jni::objects::{JClass, JString};
use jni::sys::jstring;
use pulldown_cmark::{html, Parser};


#[no_mangle]
#[allow(non_snake_case)]
pub extern "system" fn Java_io_github_ilaborie_slides2_kt_jvm_tools_MarkdownToHtml_markdownToHtml(
    env: JNIEnv,
    _class: JClass,
    input: JString,
) -> jstring {
    let input: String = env
        .get_string(input)
        .expect("Couldn't get java string!")
        .into();

    let parser = Parser::new(&input);

    let mut html_buf = String::new();
    html::push_html(&mut html_buf, parser);

    let output = env
        .new_string(html_buf)
        .expect("Couldn't create java string!");

    // Finally, extract the raw pointer to return.
    output.into_inner()
}

#[cfg(test)]
mod tests {
    #[test]
    fn it_works() {}
}

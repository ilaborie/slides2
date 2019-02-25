extern crate jni;

use jni::JNIEnv;
use jni::objects::{JClass, JString};
use wkhtmltopdf::PdfApplication;
use wkhtmltopdf::Orientation;
use wkhtmltopdf::Size;

#[no_mangle]
#[allow(non_snake_case)]
pub extern "system" fn Java_io_github_ilaborie_slides2_kt_jvm_tools_HtmlToPdf_htmlToPdf(
    env: JNIEnv,
    _class: JClass,
    title: JString,
    input: JString,
    output: JString,
) {
    let title: String = env
        .get_string(title)
        .expect("Couldn't get java string!")
        .into();
    let input: String = env
        .get_string(input)
        .expect("Couldn't get java string!")
        .into();
    let output: String = env
        .get_string(output)
        .expect("Couldn't get java string!")
        .into();

    let mut pdf_app = PdfApplication::new().expect("Failed to init PDF application");

    let mut pdfout = pdf_app.builder()
        .orientation(Orientation::Landscape)
        .margin(Size::Millimeters(15))
        .title(&title)
        .build_from_html(&input)
        .expect("failed to build pdf");

    pdfout.save(&output)
        .expect(&output);

}

#[cfg(test)]
mod tests {
    #[test]
    fn it_works() {}
}

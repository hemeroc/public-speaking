use std::ffi::{CString, CStr};
use std::os::raw::c_char;

#[no_mangle]
pub extern "C" fn rot(name: *const c_char, rotation: i32) -> *const c_char {
    let c_str = unsafe { CStr::from_ptr(name) };
    let name_str = c_str.to_str().expect("Invalid UTF-8 string");
    let rotated_str: String = name_str.chars().map(|c| rotate_char(c, rotation)).collect();
    let result = CString::new(rotated_str).expect("CString::new failed");
    result.into_raw()
 }

 fn rotate_char(c: char, rotation: i32) -> char {
    if c.is_ascii_alphabetic() {
        let start = if c.is_ascii_lowercase() { b'a' } else { b'A' };
        let offset = (c as u8 - start + rotation as u8) % 26;
        (start + offset) as char
     } else {
        c
     }
 }

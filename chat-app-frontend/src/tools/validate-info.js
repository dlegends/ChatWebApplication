// Username is valid iff it is alphanumeric and length is between 5 and 16, exclusive.
export function isValidUsername(username) {
  return isAlphaNumeric(username) && username.length > 5 && username.length < 16;
}

function isAlphaNumeric(str) {
  var code, i;

  for (i = 0; i < str.length; i++) {
    code = str.charCodeAt(i);
    if (!(code > 47 && code < 58) && // numeric (0-9)
        !(code > 64 && code < 91) && // upper alpha (A-Z)
        !(code > 96 && code < 123)) { // lower alpha (a-z)
      return false;
    }
  }
  return true;
};

function containsAlphaNumeric(str) {
  let code, i;
  let hasAlpha = false;
  let hasNumeric = false;
  for (i = 0; i < str.length; i++) {
    code = str.charCodeAt(i);

    if (code > 47 && code < 58) {  // numeric (0-9)
      hasNumeric = true;
    }

    if ((code > 64 && code < 91) || (code > 96 && code < 123)) {  // upper alpha (A-Z) or lower alpha (a-z)
      hasAlpha = true;
    }
  }

  return hasAlpha && hasNumeric;
}

export function isValidPassword(password) {
  // Considering expanding to contain certain special characters
  return containsAlphaNumeric(password) && password.length > 5 && password.length < 30;
}

export function getAccessToken() {
  const token = localStorage.getItem('accessToken');
  return token;
}
export function getRefreshToken(){
  const refreshToken = localStorage.getItem('refreshToken');
  return refreshToken;
}
// export function extractRoles(token) {
//   const decodedToken = jwt_decode(token);
//   const roles = decodedToken.roles;
//   return roles;
// }

export function extractRoles(token){
      if(token !== null ){
        let payload = token.split(".")[1];
      // decode the payload using window.atob
      let decoded = window.atob(payload);
      // parse the decoded string as a JSON object
      let parsed = JSON.parse(decoded);
      // return the role property of the object
      // console.log(parsed.roles);
      return parsed.roles;
      }
    };
    
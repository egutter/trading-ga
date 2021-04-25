# Renew refresh token
## Steps
1.
Get Consumer Key from https://developer.tdameritrade.com/user/me/apps
In the browser go To
https://auth.tdameritrade.com/auth?response_type=code&redirect_uri=http%3A%2F%2F127.0.0.1&client_id=<Consumer Key>%40AMER.OAUTHAP

2.
Copy the text in the URL after the code and decode it with irb
require 'cgi'
> CGI.escape "code"

3. Go to https://developer.tdameritrade.com/authentication/apis/post/token-0
Fill
grant_type = authorization_code
refresh_token = <empty>
access_type = offline
code = <from prev step>
client_id = <Consumer Key>@AMER.OAUTHAP
redirect_uri = http://127.0.0.1

Hit send
from the response, copy the refresh_token in the tda.token.refresh from tda-api.properties

Guide: https://developer.tdameritrade.com/content/simple-auth-local-apps

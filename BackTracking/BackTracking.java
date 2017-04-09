
public class BackTracking {

  // 93. Restore IP Addresses
  public List<String> restoreIpAddresses(String s) {
        List<String> list = new ArrayList<String>();
        if (s == null || s.length() < 4) {
            return list;
        }

        int len = s.length();
        // 1st
        for (int a = 0; a < 3; a++) {
            if (isValid(s.substring(0, a + 1))) {

                // 2nd
                for (int b = a + 1; b < a + 4 && b < len; b++) {
                    if (isValid(s.substring(a + 1, b + 1))) {

                        // 3rd
                        for (int c = b + 1; c < b + 4 && c < len; c++) {
                            if (isValid(s.substring(b + 1, c + 1))) {

                                // 4th
                                if (isValid(s.substring(c + 1))) {
                                    StringBuilder sb = new StringBuilder();
                                    sb.append(s.substring(0, a + 1))
                                      .append(".")
                                      .append(s.substring(a + 1, b + 1))
                                      .append(".")
                                      .append(s.substring(b + 1, c + 1))
                                      .append(".")
                                      .append(s.substring(c + 1));
                                    list.add(sb.toString());
                                }
                            }
                        }
                    }
                }
            }
        }

        return list;
    }

    private boolean isValid(String s) {
        if (s == null || s.length() == 0 || s.length() > 3 || (s.length() > 1 && s.charAt(0) == '0') || Integer.parseInt(s) > 255) {
            return false;
        }
        return true;
    }
  /*
  test cases
  "123"
  "000"
  "0000"
  "25525511135"
  "51232342"
  "19216810062"
  "10001"
  "0279245587303"
  */

  // 468. Validate IP Address
  // Check whether a given string is a valid IP address
  public String validIPAddress(String IP) {
        if (IP == null || IP.length() == 0) {
            return "Neither";
        }

        if (IP.contains(".") && isValidIPv4(IP)) {
            return "IPv4";
        }
        else if (IP.contains(":") && isValidIPv6(IP)){
            return "IPv6";
        }

        return "Neither";
    }

    // Check whether a given string is a valid IPv4 address
    private boolean isValidIPv4(String IP) {
        if (IP.charAt(0) == '.' || IP.charAt(IP.length() - 1) == '.') {
            return false;
        }
        String[] tokens = IP.split("\\.");
        if (tokens.length != 4) {
            return false;
        }
        for (int i = 0; i < tokens.length; i++) {
            String str = tokens[i];
            if (str.length() == 0 || str.length() > 3 || (str.length() > 1 && str.charAt(0) == '0')
                    || !str.matches("[0-9]+") || Integer.parseInt(str) > 255) {
                        return false;
                    }
        }
        return true;
    }

    // Check whether a string is a valid IPv6 address
    private boolean isValidIPv6(String IP) {
        if (IP.charAt(0) == ':' || IP.charAt(IP.length() - 1) == ':') {
            return false;
        }
        String[] tokens = IP.split(":");
        if (tokens.length != 8) {
            return false;
        }
        for (int i = 0; i < tokens.length; i++) {
            String str = tokens[i].toLowerCase();
            if (str.length() == 0 || str.length() > 4 || !str.matches("[0-9a-f]+")) {
                return false;
            }
        }
        return true;
    }

    /*
    test cases
    "2001:0db8:85a3:0:0:8A2E:0370:7334:"
    ",2001:0db8:85a3:0:0:8A2E:0370:7334:"
    ":2001:0db8:85a3:0:0:8A2E:0370:7334"
    "0.0.0"
    "172.16.254.1.1"
    "172.16.254.1."
    ".172.16.254.1"
    "172.16.254.1:1"
    "172.16.254.1"
    "172.16.254.01"
    "172.abc.1e4.01"
    "172.abc.1e4.01"
    "2001:0db8:85a3::8A2E:0370:7334"
    "2001:0db8:85a3:0000:0000:8a2e:0370:7334"
    "2001:db8:85a3:0:0:8A2E:0370:7334"
    "2001:db8:85a3:0:0:8A2E4:0:7334"
    "02001:0db8:85a3:0000:0000:8a2e:0370:7334"
    "20EE:Fb8:85a3:0:0:8A2E:0370:7334"
    */


}

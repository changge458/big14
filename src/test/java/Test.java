public class Test {


    public static void main(String[] args) {

        String s = "\\x80\\x90\\x80\\xD0\\x81\\x00\\x81@\\x81p\\x81\\x90\\x81\\xB0\\x81\\xC0\\x81\\xE0\\x82\\x10\\x82P\\x82p\\x82\\xA0\\x82\\xD0\\x82\\xF0\\x83\\x10\\x830\\x83@\\x83p\\x83\\x80\\x83\\xC0\\x83\\xF0\\x84\\x10\\x84\\x840\\x84@\\x84\\xA0\\x84\\xD0\\x84\\xF0\\x85\\x10\\x850\\x85`\\x85\\x90\\x86\\x10\\x86\\x86P\\x86p\\x86\\x90\\x86\\xA0\\x86\\xD0\\x86\\xE0\\x87\\x10\\x87P\\x87\\x80\\x87\\xB0\\x87\\xC0\\x87\\xF0\\x880\\x88P\\x88p\\x88\\xA0\\x88\\xC0\\x88\\xE0\\x88\\xF0\\x89\\x10\\x890\\x89@\\x89p\\x89\\xA0\\x89\\xD0\\x89\\xE0\\x8A\\x10\\x8A0\\x8AP\\x8Ap\\x8A\\x90\\x8A\\xB0\\x8A\\xD0\\x8A\\xF0\\x8B \\x8B@\\x8Bp\\x8B\\x90\\x8B\\xB0\\x8B\\xC0\\x8B\\xE0\\x8C\\x00\\x8C\\x8C@\\x8C`\\x8C\\x80\\x8C\\x90\\x8C\\xB0\\x8C\\xD0\\x8C\\xE0\\x8D\\x00\\x8D@\\x8Dp\\x8D\\x90\\x8D\\xB0\\x8D\\xE0\\x8E\\x00\\x8E\\x8E@\\x8E\\xC0\\x8E\\xF0\\x8F \\x8FP\\x8F\\x80\\x90\\x00\\x900\\x90`\\x90\\xB0\\x90\\xD0\\x90\\xF0\\x91\\x10\\x91P\\x91\\x80\\x91\\x90\\x91\\xB0\\x95\\xA0\\x95\\xB0\\x95\\xC0\\x95\\xD0\\x95\\xF0\\x96\\x00\\x96\\x10\\x96\\x960\\x96@\\x96P\\x96`\\x96p\\x96\\x90\\x96\\xA0\\x96\\xB0\\x9Ap\\x9B \\x9BP\\x9Bp\\x9B\\x90\\x9B\\xC0\\x9B\\xE0\\x9B\\xF0\\x9C0\\x9Cp\\x9C\\x90\\x9C\\xC0\\x9C\\xE0\\x9D\\x10\\x9D@\\x9D`\\x9D\\x90\\x9D\\xC0\\x9F\\x90\\x9F\\xB0\\x9F\\xC0\\x9F\\xD0\\x9F\\xE0\\xA0\\x00\\xA0@\\xA6\\x80\\xA6\\xC0\\xA6\\xE0\\xA7\\x10\\xA70\\xA7@\\xA7P\\xA7p\\xA7\\x90\\xA7\\xA0\\xA7\\xB0\\xA7\\xC0\\xA7\\xE0\\xA8@\\xA8`\\xA8\\x80\\xA8\\x90\\xA8\\xD0\\xA9\\x10\\xA9\\xA9@\\xA9P\\xA9`\\xA9\\x80\\xA9\\xA0\\xA9\\xC0\\xA9\\xE0\\xAA\\x10\\xAA`\\xAA\\x80\\xAA\\xB0\\xAA\\xE0\\xAB\\x00\\xB1\\x00\\xB1\\x10\\xB10\\xB1@\\xB1P\\xB1`\\xB1p\\xB1\\x80\\xB3\\xF0\\xB4\\x00\\xB4\\x10\\xB4 \\xB40\\xB4@\\xB4P\\xB4\\x80\\xB9\\xB9@\\xB9`\\xB9p\\xBAP\\xBA\\x80\\xBA\\xA0\\xBA\\xB0\\xBA\\xC0\\xBA\\xD0\\xBA\\xE0\\xBA\\xF0\\xBB\\x10\\xBB \\xBB0\\xBB@\\xBBP\\xBB`\\xBBp\\xBB\\xE0\\xBC\\x90\\xBD \\xBD\\xC0\\xBE`\\xBF\\x00\\xC0\\xC0\\xC1P\\xC1\\xA0\\xC1\\xF0\\xC2P\\xC5\\xC0\\xD6\\xF0\\xD7\\x00\\xD7\\x10\\xD7\\xD70\\xD7@\\xD7`\\xD7p\\xD7\\x80\\xD7\\x90\\xD7\\xA0\\xD7\\xB0\\xD7\\xC0\\xD7\\xD0\\xD7\\xE0\\xD7\\xF0\\xD8\\x00\\xD8\\x10\\xD8 \\xD80\\xD8@\\xD8P\\xD8\\xB0\\xD9@\\xD9\\xF0\\xDA\\x80\\xDB \\xDB\\xC0\\xDC`\\xDD\\x10\\xDD\\xA0\\xDE@\\xDE\\xE0\\xDF\\x80\\xE0\\xE0\\xD0";

        String s1 = "\\x80\\x90\\x80\\xD0\\x81\\x00\\x81";

        String xx = "我爱你";
        System.out.println(xx.getBytes());


       // String s2 = hexStringToString(s);

       // System.out.println(s2);

    }





    public static String hexStringToString(String s) {
        if (s == null || s.equals("")) {
            return null;
        }
        s = s.replace(" ", "");
        byte[] baKeyword = new byte[s.length() / 2];
        for (int i = 0; i < baKeyword.length; i++) {
            try {
                baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(i * 2, i * 2 + 2), 16));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            s = new String(baKeyword, "UTF-8");
            new String();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return s;
    }


}

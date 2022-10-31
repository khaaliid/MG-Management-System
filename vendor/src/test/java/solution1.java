import java.util.HashMap;

class solution1 {
    public static boolean isIsomorphic(String s, String t) {
        HashMap<String, Character> cache = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            if (cache.containsKey(s.charAt(i)+"1")) {
                if (!cache.get(s.charAt(i)+"1").equals(t.charAt(i))) return false;


            } else if(cache.containsKey(t.charAt(i)+"2")){


                if(!cache.get(t.charAt(i)+"2").equals(s.charAt(i))) return false;

                cache.put(s.charAt(i)+"1", t.charAt(i));


            }else{
                cache.put(s.charAt(i)+"1", t.charAt(i));
                cache.put(t.charAt(i)+"2", s.charAt(i));
            }
        }
        return true;
    }



        public static void main(String[] args) {

        System.out.println("result"+isIsomorphic("egg", "add")); //true
            System.out.println("result"+isIsomorphic("foo", "bar")); //false
            System.out.println("result"+isIsomorphic("paper", "title")); //true
            System.out.println("result"+isIsomorphic("badc" ,"baba")); //false
    }
}
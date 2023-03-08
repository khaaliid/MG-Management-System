//class ListNode {
//      int val;
//      ListNode next;
//      ListNode() {}
//      ListNode(int val) { this.val = val; }
//      ListNode(int val, ListNode next) { this.val = val; this.next = next; }
//
//    @Override
//    public String toString() {
//        StringBuilder s = new StringBuilder();
//        s.append(this.val);
//        ListNode tmp = this;
//        while(tmp.next!=null){
//            s.append(">"+tmp.next.val);
//            tmp=tmp.next;
//        }
//        return s.toString();
//    }
//}
//
//class solution {
//    public static ListNode mergeTwoLists(ListNode list1, ListNode list2) {
//    hhhhhhhhhhhhhhhhhhhhhhhhhhhh
//    return result;
//    }
//
//
//    public static void main(String[] args) {
//
//
//        ListNode a = new ListNode(1);
//        ListNode a1 = new ListNode(2);
//        a.next=a1;
//        ListNode a2 = new ListNode(4);
//        a1.next=a2;
//        a2.next=null;
//
//
//        ListNode b = new ListNode(1);
//        ListNode b1 = new ListNode(3);
//        b.next=b1;
//        ListNode b2 = new ListNode(4);
//        b1.next=b2;
//        b2.next=null;
//
////        System.out.println(a+" = "+b); //true
//        System.out.println("result"+mergeTwoLists(a, b)); //true
////        System.out.println("result"+find("axc","ahbgdc")); // false
////        System.out.println("result"+find("","")); //true
////        System.out.println("result"+find("acb","ahbgdc"));//false
////        System.out.println("result"+find("ab","baab"));//true
//    }
//}


class Solution {
    public int maxSubArray(int[] nums) {
        if (nums.length == 1) return nums[0];
        int max = nums[0];
        int tmpMax = nums[1];
        for (int i = 1; i < nums.length - 1; i += 2) {
            max += nums[i];
            tmpMax += nums[i + 1];
            if (max < tmpMax) {
                max = tmpMax;
                i++;
            }
        }
        return max;
    }


    public static void main(String[] args) {
        new Solution().maxSubArray(new int[] {-2,1,-3,4,-1,2,1,-5,4});
    }


}
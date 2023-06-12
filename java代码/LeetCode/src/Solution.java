import java.util.ArrayList;
import java.util.Arrays;

/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {
    public ListNode sortList(ListNode head) {
        ArrayList<ListNode> arr=new ArrayList<>();
        while(head!=null){
            arr.add(head);
            head=head.next;
        }

        for(int i=0;i<arr.size();i++){
            arr.get(i).next=arr.get(i+1);
        }
        return arr.get(0);
    }
}
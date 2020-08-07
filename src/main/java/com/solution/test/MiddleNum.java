package com.solution.test;

public class MiddleNum {

	/**
	 * 给定两个大小�? m �? n 的有序数组 nums1 和 nums2�? 请你找出这两个有序数组的中位数，并且要求算法的时间复杂度为 O(log(m +
	 * n))�? 你可以假设 nums1 和 nums2 不会同时为空�? 示例 1: nums1 = [1, 3] nums2 = [2] 则中位数�? 2.0
	 * 示例 2: nums1 = [1, 2] nums2 = [3, 4] 则中位数�? (2 + 3)/2 = 2.5
	 *
	 * @param nums1
	 * @param nums2
	 * @return
	 */
	public double countMiddleNumResult(int[] nums1, int[] nums2) {
		int totalLength = nums1.length + nums2.length;
		int point1 = 0;
		int point2 = 0;
		int left = 0;
		int right = 0;
		for (int i = 0; i <= totalLength / 2; i++) {
			left = right;
			if (point2 >= nums2.length || (point1 < nums1.length && nums1[point1] <= nums2[point2])) {
				right = nums1[point1];
				point1++;
			} else {
				right = nums2[point2];
				point2++;
			}
		}
		if ((totalLength & 1) == 0) {
			return (left + right) / 2D;
		}
		return right;
	}

	public double findMedianSortedArrays(int[] nums1, int[] nums2) {
		if (nums1.length > nums2.length) {
			int[] t = nums1;
			nums1 = nums2;
			nums2 = t;
		}

		int length1 = nums1.length;
		int length2 = nums2.length;
		int totalLength = length1 + length2;

		/* 当nums1元素个数为空 */
		if (length1 == 0) {
			if ((length2 & 1) == 0) {
				return (nums2[length2 / 2 - 1] + nums2[length2 / 2]) / 2.0;
			} else {
				return nums2[length2 / 2];
			}
		}

		/* 当nums1元素个数�?1 */
		if (length1 == 1) {
			if (length2 == 1) {
				return (nums1[0] + nums2[0]) / 2.0;
			}
			if ((length2 & 1) == 0) {
				if (nums1[0] <= nums2[length2 / 2 - 1]) {
					return nums2[length2 / 2 - 1];
				} else if (nums1[0] <= nums2[length2 / 2]) {
					return nums1[0];
				} else {
					return nums2[length2 / 2];
				}
			} else {
				if (nums1[0] <= nums2[length2 / 2 - 1]) {
					return (nums1[0] + nums2[length2 / 2]) / 2.0;
				} else if (nums1[0] <= nums2[length2 / 2]) {
					return (nums2[length2 / 2 - 1] + nums2[length2 / 2]) / 2.0;
				} else {
					return (nums2[length2 / 2] + nums2[length2 / 2 + 1]) / 2.0;
				}
			}
		}


		int point1 = (nums1.length) / 2;
		int point2 = totalLength / 2 - point1;
		int left1 = -1;
		int left2 = -1;
		int right1 = -1;
		int right2 = -1;
		while (true) {
			left1 = nums1[point1 - 1];
			left2 = nums2[point2 - 1];
			right1 = nums1[point1];
			right2 = nums2[point2];
			if (left1 < left2 && right1 < right2 && left2 > right1) {
				point1 += (length1 - point1) / 2;
				point2 -= (length1 - point1) / 2;
			} else if (left1 > left2 && right1 > right2 && left1 > right2) {
				point1 -= (point1 + 1) / 2;
				point2 += (point1 + 1) / 2;
			} else {
				break;
			}
		}

		if ((totalLength & 1) == 0) {
			if (left1 > left2) {
				return (left1 + right2) / 2.0;
			} else {
				return (left2 + right1) / 2.0;
			}
		} else {
			if (left1 > left2) {
				return left1;
			} else {
				return left2;
			}
		}

	}

	public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
		int val1 = 0;
		int val2 = 0;
		int sum = 0;
		int over = 0;
		ListNode result = new ListNode(0);
		ListNode curResult = result;
		while (l1 != null || l2 != null) {
			val1 = l1 == null ? 0 : l1.val;
			val2 = l2 == null ? 0 : l2.val;
			sum = val1 + val2 + over;
			over = sum / 10;
			curResult.next = new ListNode(sum % 10);
			curResult = curResult.next;
			if(l1 != null){
				l1 = l1.next;
			}
			if(l2 != null) {
				l2 = l2.next;
			}
		}
		if(over > 0) {
			curResult.next = new ListNode(over);
		}
		return result.next;
	}

	private double extracted(int[] nums1, int[] nums2, int totalLength, int point1, int point2) {
		if ((totalLength & 1) == 0) {
			if (nums1[point1] > nums2[point2]) {
				return (nums1[point1 - 1] + nums2[point2]) / 2.0;
			} else {
				return (nums2[point2 - 1] + nums1[point1]) / 2.0;
			}
		} else {
			if (nums1[point1] > nums2[point2]) {
				return nums1[point1];
			} else {
				return nums2[point2];
			}
		}
	}

	public static void main(String[] args) {
//		int[] nums1 = { 3, 4};
//		int[] nums2 = { 1, 2};
//		MiddleNum middleNum = new MiddleNum();
//		System.out.println(middleNum.countMiddleNumResult(nums1, nums2));
//		System.out.println(middleNum.findMedianSortedArrays(nums1, nums2));

		MiddleNum middleNum = new MiddleNum();
		ListNode listNode1 = new ListNode(1);
		ListNode listNode2 = new ListNode(8);
		ListNode listNode3 = new ListNode(3);
		ListNode listNode4 = new ListNode(9);
		ListNode listNode5 = new ListNode(9);
		ListNode listNode6 = new ListNode(4);
//		listNode1.next = listNode2;
//		listNode2.next = listNode3;
		listNode4.next = listNode5;
//		listNode5.next = listNode6;
		ListNode result = middleNum.addTwoNumbers(listNode1, listNode4);
		System.out.println(result);
	}

}

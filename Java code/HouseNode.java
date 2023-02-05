// --== CS400 Role Code Project HouseNode==--
// Name: Ahmet Ahunbay
// CSL Username: ahunbay
// Email: aaahunbay@wisc.edu
// Lecture #: 003 @2:25pm
// Notes to Grader: N/A

/**
 * Node used to store house objects within the RBTree
 * @author ahmet ahunbay
 *
 */
public class HouseNode {
		protected int blackHeight = 0;
		protected HouseNode rightChild;
		protected HouseNode leftChild;
		protected HouseNode parent;
		protected House data;
		
		public HouseNode(HouseNode parent, boolean isLeft) {
			this.blackHeight =2;
			this.parent = parent;
			if(isLeft) {
				parent.leftChild = this;
			}else {
				parent.rightChild = this;
			}
		}
		public HouseNode(House data) {
			this.data = data; 

		}
			
		public int getValue() {
			return data.getPrice();
		}
		public int compareTo(HouseNode other) {
			return this.data.compareTo(other.data);
		}
		
		public boolean equals(HouseNode other) {
			if(other != null && other.data != null && this.data != null) {
				return this.data.compareTo(other.data) == 0;
			}
			return false;
		}
		
		public boolean isLeftChild() {
			return this.parent != null && this.parent.leftChild != null && this.parent.leftChild.equals(this);
		}
		
		public boolean isRightChild() {
			return this.parent != null && this.parent.rightChild != null && this.parent.rightChild.equals(this);
		}
		
	

	}
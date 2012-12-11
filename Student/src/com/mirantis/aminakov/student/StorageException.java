package com.mirantis.aminakov.student;

public class StorageException extends Exception {

	private static final long serialVersionUID = 1L;

}

class DeleteException extends StorageException {

	private static final long serialVersionUID = 1L;
	
}

class ListIsEmpty extends DeleteException {

	private static final long serialVersionUID = 1L;
	
}

class NoStudent extends DeleteException {

	private static final long serialVersionUID = 1L;
	
}

class AddException extends StorageException {

	private static final long serialVersionUID = 1L;

}

class OutOfCapacity extends AddException {

	private static final long serialVersionUID = 1L;

}

class NotEnoughMemory extends OutOfCapacity {

	private static final long serialVersionUID = 1L;

}


package org.burbridge.sandbox.api.error

class RecordNotFoundException(id: String) : RuntimeException("Could not find record: $id")

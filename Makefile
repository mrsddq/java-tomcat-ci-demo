.PHONY: verify clean
verify:
	mvn --batch-mode --no-transfer-progress clean verify
clean:
	mvn --batch-mode --no-transfer-progress clean

#!/bin/bash

llvm-as -f output.ll && lli output.bc

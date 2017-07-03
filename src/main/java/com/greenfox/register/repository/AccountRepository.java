package com.greenfox.register.repository;

import com.greenfox.register.model.Account;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AccountRepository extends PagingAndSortingRepository<Account, Long> {
  Account findAccountByEmail(String email);
}

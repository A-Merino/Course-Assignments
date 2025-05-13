program array_operations
   implicit none
   integer :: N, t, i, j
   integer, allocatable :: A(:)
   
   read(*, *) N, t
   allocate(A(N))
   read(*, *) (A(i), i = 1, N)
   
   select case(t)
   case(1)
       call check_sum_7777(A, N)
   case(2)
       call check_unique(A, N)
   case(3)
       call find_majority(A, N)
   case(4)
       call find_median(A, N)
   case(5)
       call print_range(A, N)
   end select
   
   deallocate(A)
contains
   subroutine check_sum_7777(A, N)
       integer, intent(in) :: A(N), N
       integer :: i, j
       do i = 1, N
           do j = i + 1, N
               if (A(i) + A(j) == 7777) then
                   print '(A)', 'Yes'
                   return
               end if
           end do
       end do
       print '(A)', 'No'
   end subroutine
   
   subroutine check_unique(A, N)
       integer, intent(in) :: A(N), N
       integer :: i, j
       do i = 1, N
           do j = i + 1, N
               if (A(i) == A(j)) then 
                   print '(A)', 'Contains duplicate'
                   return
               end if
           end do
       end do
       print '(A)', 'Unique'
   end subroutine
       
   subroutine find_majority(A, N)
       integer, intent(in) :: A(N), N
       integer :: i, j, count
       do i = 1, N
           count = 0
           do j = 1, N
               if (A(j) == A(i)) count = count + 1
           end do
           if (2 * count > N) then
               print *, A(i)
               return
           end if
       end do
       print *, -1
   end subroutine
   
   subroutine sort(arr, n)
       integer, intent(inout) :: arr(n)
       integer, intent(in) :: n
       integer :: i, j, temp
       do i = 1, n-1
           do j = 1, n-i
               if (arr(j) > arr(j+1)) then
                   temp = arr(j)
                   arr(j) = arr(j+1)
                   arr(j+1) = temp
               end if
           end do
       end do
   end subroutine
   
   subroutine find_median(A, N)
       integer, intent(in) :: A(N), N
       integer :: temp(N)
       temp = A
       call sort(temp, N)
       if (mod(N,2) == 1) then
           write(*, '(I0)') temp((N + 1) / 2)
       else
           write(*, '(I0,A,I0)') temp(N/2), ' ', temp(N/2 + 1)
       end if
   end subroutine
   
   subroutine print_range(A, N)
       integer, intent(in) :: A(N), N
       integer :: temp(N), i
       logical :: first
       temp = A
       call sort(temp, N)
       first = .true.
       do i = 1, N
           if (temp(i) >= 100 .and. temp(i) <= 999) then 
               if (first) then
                   write(*, '(I0)', advance='no') temp(i)
                   first = .false.
               else
                   write(*, '(A,I0)', advance='no') ' ', temp(i)
               end if
           end if
       end do
       print *
   end subroutine
end program
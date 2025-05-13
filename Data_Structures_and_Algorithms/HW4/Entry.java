public interface Entry<B,T,N> {
  // returns bid stored in entry
  B getBid();

  // returns time stored in entry
  T getTime();

  // returns name stored in entry
  N getName();
}
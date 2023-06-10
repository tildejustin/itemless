## Itemless

What this mod does is allow you to see negative stack amounts rendered, which a helpful visualization for making 0 and negative stacks in 1.3.2. It works from versions 1.3.0 to 1.12.2, but between 1.8.0 and 1.12.0, the mod does nothing (this is because negative stacks are already rendered in that version range). In 1.12.1-1.12.2 it has to stop the render from skipping if the stacksize is <= 0, and adapts to using a getter instead of a field access, but otherwise it's the same thing for all versions. This was mainly targetted at 1.3-1.5 but basically I had a lot of time on my hands :P.

v1.0.0 renders "1" on single items, v1.0.1 does not. I guess it's personal preference, but imo "1"s are just unnecessary.
